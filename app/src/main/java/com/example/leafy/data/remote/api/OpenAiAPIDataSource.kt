package com.example.leafy.data.remote.api

import com.example.leafy.BuildConfig
import com.example.leafy.data.models.ChatRequest
import com.example.leafy.data.models.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAIApiDataSource {
    @POST("chat/completions")
    suspend fun sendMessage(
        @Header("Authorization") token: String = BuildConfig.API_GIGACHAT ,
        @Body request: ChatRequest
    ): ChatResponse
}