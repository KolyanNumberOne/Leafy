package com.example.leafy.data.remote.api

import com.example.leafy.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Header

interface RemotePlantDataSource {

    @GET("plants/name_search")
    suspend fun searchPlants(
        @Query("token") token: String = BuildConfig.API_KEY,
        @Query("lang") language: String = "ru",
        @Query("thumbnails") thumbnails: Boolean = true,
        @Query("limit") limit: Int = 10,
        @Query("q") query: String,
    ): Response<Any>

    @GET("plants")
    suspend fun listPlants(
        @Query("token") token: String = BuildConfig.API_KEY,
    ): Response<Any>

    @POST("identification")
    suspend fun imageSearch(
        @Header("API-Key") apiKey: String = BuildConfig.API_KEY,
        @Body requestBody: PlantIdentificationRequest
    ): Response<Any>

    data class PlantIdentificationRequest(
        val images: List<String>,        // Изображения в формате Base64
        val latitude: Double,
        val longitude: Double,
        val similar_images: Boolean
    )

}
