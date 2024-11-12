package com.example.leafy.data.remote.api

import com.example.leafy.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemotePlantDataSource {

    @GET("search/plants")
    suspend fun searchPlants(
        @Query("token") token: String = BuildConfig.API_KEY_TREFLE,
        @Query("q") query: String
    ): Response<Any>

    @GET("plants")
    suspend fun listPlants(
        @Query("token") token: String = BuildConfig.API_KEY_TREFLE,
    ): Response<Any>
}
