package com.example.leafy.data.remote.api

import com.example.leafy.BuildConfig
import com.example.leafy.data.models.ImageSearchRequest
import com.example.leafy.data.models.PlantResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PlantApiDataSource {

    @POST("identification")
    suspend fun imageSearch(
        @Header("API-Key") apiKey: String = BuildConfig.API_KEY,
        @Body requestBody: ImageSearchRequest
    ): PlantResponse

}