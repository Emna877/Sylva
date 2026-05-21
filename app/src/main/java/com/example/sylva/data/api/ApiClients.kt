package com.example.sylva.data.api

import com.example.sylva.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClients {

    private val logger = HttpLoggingInterceptor().apply {
        // Use BODY level during development to see request/response payloads from Plant.id/Gemini.
        // You can lower this to BASIC or NONE for release builds.
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Add a small interceptor to ensure Plant.id requests always include the Api-Key header
    // and to provide helpful defaults for timeouts during network calls.
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logger)
        .addInterceptor(Interceptor { chain ->
            val request = chain.request()
            val host = request.url.host
            val newRequestBuilder = request.newBuilder()

            // If this request is going to plant.id, inject the Api-Key header from BuildConfig
            if (host.contains("plant.id")) {
                newRequestBuilder.header("Api-Key", BuildConfig.PLANT_ID_API_KEY)
                newRequestBuilder.header("Accept", "application/json")
            }

            chain.proceed(newRequestBuilder.build())
        })
        // Reasonable timeouts for network calls to AI services
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val plantRetrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.PLANT_ID_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val geminiRetrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.GEMINI_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val plantIdService: PlantIdService = plantRetrofit.create(PlantIdService::class.java)
    val geminiService: GeminiService = geminiRetrofit.create(GeminiService::class.java)
}

