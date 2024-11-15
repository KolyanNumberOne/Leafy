package com.example.leafy.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteListSearchPlant(
    val entities: List<SearchPlant>
)

@Serializable
data class SearchPlant(
    @SerialName("matched_in") val name: String,
//    @SerialName("matched_in_type") val matchedInType: String,
    @SerialName("access_token") val accessToken: String,
//    @SerialName("match_position") val matchPosition: Double,
//    @SerialName("match_length") val matchLength: Double,
//    @SerialName("entity_name") val entityName: String,
    @SerialName("thumbnail") var imageBase64: String
)