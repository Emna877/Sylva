package com.example.sylva.data.repository

import okhttp3.RequestBody.Companion.toRequestBody
import com.example.sylva.BuildConfig
import com.example.sylva.data.api.ApiClients
import com.example.sylva.data.api.GeminiContent
import com.example.sylva.data.api.GeminiPart
import com.example.sylva.data.api.GeminiRequest
import com.example.sylva.ui.model.InsightItem
import com.example.sylva.ui.model.TreeProfile
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody

data class InsightEnvelope(
    val nativeRegion: String = "Unknown",
    val ecologicalRole: String = "No data",
    val biodiversityImportance: String = "No data",
    val urbanUsefulness: String = "No data",
    val funFact: String = "No data",
    val insights: List<InsightLine> = emptyList()
)

data class InsightLine(
    val title: String = "Insight",
    val body: String = "No data"
)

class TreeRepository {

    private val plantIdService by lazy { ApiClients.plantIdService }
    private val geminiService by lazy { ApiClients.geminiService }
    private val gson by lazy { Gson() }

    suspend fun analyzeTree(imageBytes: ByteArray): Result<TreeProfile> {
        return runCatching {
            require(BuildConfig.PLANT_ID_API_KEY.isNotBlank()) { "Plant.id key is missing." }
            require(BuildConfig.GEMINI_API_KEY.isNotBlank()) { "Gemini key is missing." }

            // Send image as multipart/form-data to Plant.id
            val requestBody = imageBytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("images", "image.jpg", requestBody)
            val plantResponse = plantIdService.identifyPlantMultipart(
                apiKey = BuildConfig.PLANT_ID_API_KEY,
                image = imagePart
            )

            // Plant.id can return multiple result entries; scan them all for the strongest match.
            val topSuggestion = findTopPlantSuggestion(plantResponse)
                ?: error("No species detected by Plant.id")

            val speciesName = topSuggestion.name
            val commonNames = topSuggestion.details?.commonNames.orEmpty()
            val confidence = topSuggestion.probability.toFloat().coerceIn(0f, 1f)

            val prompt = """
                Return ONLY valid compact JSON with keys:
                nativeRegion, ecologicalRole, biodiversityImportance, urbanUsefulness, funFact, insights.
                insights must be an array of 3 objects with keys title and body.
                Species: $speciesName
            """.trimIndent()

            val geminiResponse = geminiService.generateInsights(
                apiKey = BuildConfig.GEMINI_API_KEY,
                request = GeminiRequest(contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt)))))
            )

            val geminiText = geminiResponse.candidates
                .firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text
                .orEmpty()

            val parsed = parseInsights(geminiText)

            TreeProfile(
                scientificName = speciesName,
                commonNames = if (commonNames.isEmpty()) listOf("Unknown common name") else commonNames,
                confidence = confidence,
                nativeRegion = parsed.nativeRegion,
                biodiversityImportance = parsed.biodiversityImportance,
                urbanUsefulness = parsed.urbanUsefulness,
                ecologicalRole = parsed.ecologicalRole,
                funFact = parsed.funFact,
                insights = parsed.insights.map {
                    InsightItem(
                        title = it.title,
                        body = it.body,
                        iconLabel = it.title.lowercase().replace(" ", "_")
                    )
                }
            )
        }
    }

    private fun parseInsights(text: String): InsightEnvelope {
        if (text.isBlank()) return InsightEnvelope()

        return runCatching {
            val jsonStart = text.indexOf('{')
            val jsonEnd = text.lastIndexOf('}')
            if (jsonStart == -1 || jsonEnd == -1 || jsonEnd <= jsonStart) {
                return@runCatching InsightEnvelope(
                    ecologicalRole = text,
                    biodiversityImportance = text,
                    urbanUsefulness = text,
                    funFact = text,
                    insights = listOf(InsightLine("Overview", text))
                )
            }
            val rawJson = text.substring(jsonStart, jsonEnd + 1)
            gson.fromJson(rawJson, InsightEnvelope::class.java)
        }.getOrDefault(
            InsightEnvelope(
                ecologicalRole = text,
                biodiversityImportance = text,
                urbanUsefulness = text,
                funFact = text,
                insights = listOf(InsightLine("Overview", text))
            )
        )
    }

    internal fun findTopPlantSuggestion(response: com.example.sylva.data.api.PlantIdResponse): com.example.sylva.data.api.PlantSuggestion? {
        return response.results
            .asSequence()
            .flatMap { it.classification?.suggestions.orEmpty().asSequence() }
            .maxByOrNull { it.probability }
    }
}

