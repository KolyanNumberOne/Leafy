package com.example.leafy.data.remote.api

import com.example.leafy.data.models.PlantDetail
import retrofit2.http.GET
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



//    @POST("identification")
//    suspend fun imageSearch(
//        @Header("API-Key") apiKey: String = BuildConfig.API_KEY,
//        @Body requestBody: PlantIdentificationRequest
//    ): Response<Any>


//    data class PlantIdentificationRequest(
//        val images: List<String>,
//        val latitude: Double,
//        val longitude: Double,
//        val similar_images: Boolean
//    )

}
