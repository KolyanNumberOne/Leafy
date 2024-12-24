package com.example.leafy.data.remote.api

import com.example.leafy.BuildConfig
import com.example.leafy.data.models.AuthResponse
import com.example.leafy.data.models.ChatRequest
import com.example.leafy.data.models.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface GigaChatApiDataSource {
    @POST("chat/completions")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body request: ChatRequest
    ): ChatResponse

}

interface GigaChatAuth {
    @FormUrlEncoded
    @POST("oauth")
    @Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "Accept: application/json"
    )
    suspend fun authentication(
        @Header("RqUID") requestId: String = "6f0b1291-c7f3-43c6-bb2e-9f3efb2dc98e",
        @Header("Authorization") authorization: String = "Basic ${BuildConfig.API_GIGACHAT}",
        @Field("scope") scope: String = "GIGACHAT_API_PERS"
    ): AuthResponse
}