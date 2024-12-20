package com.example.leafy.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ImageSearchRequest(
    val images: List<String>
)