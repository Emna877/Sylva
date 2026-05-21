package com.example.sylva.data.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class GeminiRequest(
    @SerializedName("contents") val contents: List<GeminiContent>
)

data class GeminiContent(
    @SerializedName("parts") val parts: List<GeminiPart>
)

data class GeminiPart(
    @SerializedName("text") val text: String
)

data class GeminiResponse(
    @SerializedName("candidates") val candidates: List<GeminiCandidate> = emptyList()
)

data class GeminiCandidate(
    @SerializedName("content") val content: GeminiContent? = null
)

interface GeminiService {
    @POST("v1beta/models/{model}:generateContent")
    suspend fun generateInsights(
        @Path("model") model: String,
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

