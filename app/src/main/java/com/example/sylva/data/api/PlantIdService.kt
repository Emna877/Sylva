package com.example.sylva.data.api

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

// Plant.id response models. The live API has used both `result` and `results`
// shapes over time, so the model accepts both.
data class PlantIdResponse(
    @SerializedName("results") val results: List<PlantIdResult> = emptyList(),
    @SerializedName("result") val result: PlantIdResult? = null
)

fun PlantIdResponse.allResults(): List<PlantIdResult> {
    return if (results.isNotEmpty()) results else result?.let { listOf(it) }.orEmpty()
}

data class PlantIdResult(
    @SerializedName("classification") val classification: PlantIdClassification? = null
)

data class PlantIdClassification(
    @SerializedName("suggestions") val suggestions: List<PlantSuggestion> = emptyList()
)

data class PlantSuggestion(
    // Accept several name/detail field variants to be robust against Plant.id schema changes.
    @SerializedName("plant_name") val plantName: String? = null,
    @SerializedName("name") val legacyName: String? = null,
    @SerializedName("probability") val probability: Double = 0.0,
    @SerializedName("details") val details: PlantSuggestionDetails? = null,
    @SerializedName("plant_details") val plantDetails: PlantSuggestionDetails? = null
) {
    val displayName: String
        get() = when {
            !plantName.isNullOrBlank() -> plantName
            !legacyName.isNullOrBlank() -> legacyName
            else -> "Unknown species"
        }

    val resolvedDetails: PlantSuggestionDetails?
        get() = details ?: plantDetails
}

data class PlantSuggestionDetails(
    @SerializedName("common_names") val commonNames: List<String> = emptyList(),
    @SerializedName("commonNames") val commonNamesCamel: List<String> = emptyList()
)

interface PlantIdService {
    // Use multipart/form-data upload for images to match Plant.id documentation.
    @Multipart
    @POST("api/v3/identification")
    suspend fun identifyPlantMultipart(
        @Part image: MultipartBody.Part
    ): PlantIdResponse
}

