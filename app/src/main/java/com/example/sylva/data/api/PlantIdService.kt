package com.example.sylva.data.api

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

// Plant.id response models (the API returns a `results` array).
data class PlantIdResponse(
    @SerializedName("results") val results: List<PlantIdResult> = emptyList()
)

data class PlantIdResult(
    @SerializedName("classification") val classification: PlantIdClassification? = null
)

data class PlantIdClassification(
    @SerializedName("suggestions") val suggestions: List<PlantSuggestion> = emptyList()
)

data class PlantSuggestion(
    // Accept both `plant_name` (v3) and legacy `name` keys to be robust against variations.
    @SerializedName("plant_name") val plantName: String? = null,
    @SerializedName("name") val legacyName: String? = null,
    @SerializedName("probability") val probability: Double = 0.0,
    @SerializedName("plant_details") val details: PlantSuggestionDetails? = null
) {
    val displayName: String
        get() = when {
            !plantName.isNullOrBlank() -> plantName
            !legacyName.isNullOrBlank() -> legacyName
            else -> "Unknown species"
        }
}

data class PlantSuggestionDetails(
    @SerializedName("common_names") val commonNames: List<String> = emptyList()
)

interface PlantIdService {
    // Use multipart/form-data upload for images to match Plant.id documentation.
    @Multipart
    @POST("api/v3/identification")
    suspend fun identifyPlantMultipart(
        @Header("Api-Key") apiKey: String,
        @Part image: MultipartBody.Part
    ): PlantIdResponse
}

