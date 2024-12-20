package com.example.leafy.data.remote.api

import com.example.leafy.BuildConfig
import com.example.leafy.data.models.ImageSearchRequest
import com.example.leafy.data.models.PlantDetail
import com.example.leafy.data.models.PlantResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RemotePlantDataSource {

    @GET("listPlant")
    suspend fun fetchPlantsByPage(
        @Query("page") page: Int
    ): List<PlantDetail>

    @GET("searchPlant")
    suspend fun searchPlantsByName(
        @Query("name") name: String,
        @Query("page") page: Int
    ): List<PlantDetail>

}
