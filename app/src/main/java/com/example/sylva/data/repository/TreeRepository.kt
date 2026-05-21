package com.example.sylva.data.repository

import okhttp3.RequestBody.Companion.toRequestBody
import com.example.sylva.BuildConfig
import com.example.sylva.data.api.ApiClients
import com.example.sylva.data.api.GeminiContent
import com.example.sylva.data.api.GeminiPart
import com.example.sylva.data.api.GeminiRequest
import com.example.sylva.data.api.allResults
import com.example.sylva.ui.model.InsightItem
import com.example.sylva.ui.model.TreeProfile
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import android.util.Log
import retrofit2.HttpException
import okhttp3.ResponseBody

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
            // Plant.id expects images under the `images` multipart field name.
            val imagePart = MultipartBody.Part.createFormData("images", "image.jpg", requestBody)

            // Debug: log that we're about to call Plant.id and the size of the image
            Log.d("TreeRepository", "Calling Plant.id with image bytes=${imageBytes.size}")
            val plantResponse = try {
                plantIdService.identifyPlantMultipart(imagePart)
            } catch (e: HttpException) {
                // Try to extract a helpful error body to make debugging easier for 4xx/5xx responses
                val errorBody = try {
                    e.response()?.errorBody()?.string().orEmpty()
                } catch (t: Throwable) {
                    "(failed to read error body: ${t.message})"
                }
                Log.e("TreeRepository", "Plant.id HttpException code=${e.code()} body=$errorBody")
                throw RuntimeException("Plant.id request failed: HTTP ${e.code()} - $errorBody")
            }

            // Plant.id can return multiple result entries; scan them all for the strongest match.
            val plantResults = plantResponse.allResults()
            Log.d(
                "TreeRepository",
                "Plant.id response results=${plantResponse.results.size}, resultPresent=${plantResponse.result != null}, normalizedResults=${plantResults.size}"
            )
            // Log a brief snapshot of the first suggestion if available to help debug mapping issues.
            val firstSuggestion = plantResults
                .firstOrNull()
                ?.classification
                ?.suggestions
                ?.firstOrNull()
            if (firstSuggestion != null) {
                // Use displayName which handles plant_name or legacy name fields.
                val name = firstSuggestion.displayName
                Log.d("TreeRepository", "First suggestion name=$name probability=${firstSuggestion.probability}")
            } else {
                Log.d("TreeRepository", "No suggestions found in Plant.id response")
            }

            val topSuggestion = findTopPlantSuggestion(plantResponse)
                ?: error(
                    "No species detected by Plant.id. The response contained ${plantResults.size} result(s) but no usable suggestions."
                )

            val speciesName = topSuggestion.displayName
            val commonNames = (topSuggestion.resolvedDetails?.commonNames.orEmpty() + topSuggestion.resolvedDetails?.commonNamesCamel.orEmpty())
                .distinct()
            val confidence = topSuggestion.probability.toFloat().coerceIn(0f, 1f)

            val prompt = """
                Return ONLY valid compact JSON with keys:
                nativeRegion, ecologicalRole, biodiversityImportance, urbanUsefulness, funFact, insights.
                insights must be an array of 3 objects with keys title and body.
                Species: $speciesName
            """.trimIndent()

            val geminiResponse = generateGeminiInsightsWithFallback(
                prompt = prompt,
                apiKey = BuildConfig.GEMINI_API_KEY
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

    private suspend fun generateGeminiInsightsWithFallback(prompt: String, apiKey: String): com.example.sylva.data.api.GeminiResponse {
        val request = GeminiRequest(contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt)))))
        val candidateModels = listOf(
            "gemini-2.5-flash",
            "gemini-2.0-flash",
            "gemini-1.5-flash"
        )

        var lastError: Throwable? = null

        for (model in candidateModels) {
            try {
                Log.d("TreeRepository", "Calling Gemini model=$model")
                return geminiService.generateInsights(
                    model = model,
                    apiKey = apiKey,
                    request = request
                )
            } catch (e: HttpException) {
                val errorBody = try {
                    e.response()?.errorBody()?.string().orEmpty()
                } catch (t: Throwable) {
                    "(failed to read error body: ${t.message})"
                }
                Log.e("TreeRepository", "Gemini HttpException model=$model code=${e.code()} body=$errorBody")
                lastError = e
                if (e.code() != 404) {
                    throw RuntimeException("Gemini request failed for $model: HTTP ${e.code()} - $errorBody")
                }
            } catch (t: Throwable) {
                Log.e("TreeRepository", "Gemini call failed model=$model: ${t.message}", t)
                lastError = t
            }
        }

        throw RuntimeException(
            "Gemini request failed: no supported model responded successfully. Last error: ${lastError?.message ?: "unknown"}"
        )
    }

    internal fun findTopPlantSuggestion(response: com.example.sylva.data.api.PlantIdResponse): com.example.sylva.data.api.PlantSuggestion? {
        return response.allResults()
            .asSequence()
            .flatMap { it.classification?.suggestions.orEmpty().asSequence() }
            .maxByOrNull { it.probability }
    }
}

