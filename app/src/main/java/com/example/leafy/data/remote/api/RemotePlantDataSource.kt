package com.example.leafy.data.remote.api

import com.example.leafy.BuildConfig
import com.example.leafy.data.models.PlantDetail
import com.example.leafy.data.models.RemoteListSearchPlant
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Header
import retrofit2.http.Path

interface RemotePlantDataSource {

    @GET("kb/plants/name_search")
    suspend fun searchPlants(
        @Header("Api-Key") apiKey: String = BuildConfig.API_KEY,
        @Query("lang") language: String = "ru",
        @Query("thumbnails") thumbnails: Boolean = true,
        @Query("limit") limit: Int = 1,
        @Query("q") query: String,
    ): RemoteListSearchPlant

//    @GET("kb/plants")
//    suspend fun listPlants(
//        @Query("token") token: String = BuildConfig.API_KEY,
//    ): Response<Any>

//    @POST("identification")
//    suspend fun imageSearch(
//        @Header("API-Key") apiKey: String = BuildConfig.API_KEY,
//        @Body requestBody: PlantIdentificationRequest
//    ): Response<Any>

    @GET("kb/plants/{access_token}")
    suspend fun plantDetail(
        @Header("API-Key") apiKey: String = BuildConfig.API_KEY,
        @Path("access_token") accessToken: String,
        @Query("lang") language: String = "ru",
        @Query("details") details: String = "common_names,url,description,taxonomy,name_authority,rank,gbif_id,inaturalist_id,image,images,synonyms,edible_parts,propagation_methods,watering,best_watering,best_light_condition,best_soil_type,common_uses,toxicity,cultural_significance",
    ): PlantDetail

//    data class PlantIdentificationRequest(
//        val images: List<String>,
//        val latitude: Double,
//        val longitude: Double,
//        val similar_images: Boolean
//    )

}
