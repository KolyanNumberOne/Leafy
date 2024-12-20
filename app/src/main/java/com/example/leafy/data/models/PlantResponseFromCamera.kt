package com.example.leafy.data.models

import kotlinx.serialization.Serializable

@Serializable
data class PlantResponse(
    val result: PlantResult
)

@Serializable
data class PlantResult(
    val classification: Classification
)

@Serializable
data class Classification(
    val suggestions: List<PlantSuggestion>
)

@Serializable
data class PlantSuggestion(
    val name: String,
    val probability: Float
)
