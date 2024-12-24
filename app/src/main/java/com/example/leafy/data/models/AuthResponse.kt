package com.example.leafy.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("access_token")
    val accessToken: String,

    @SerialName("expires_at")
    val expiresAt: Long
)