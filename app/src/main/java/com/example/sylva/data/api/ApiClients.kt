package com.example.sylva.data.api

import com.example.sylva.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClients {

    private val logger = HttpLoggingInterceptor().apply {
        // Use BODY level during development to see request/response payloads from Plant.id/Gemini.
        // You can lower this to BASIC or NONE for release builds.
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logger)
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

