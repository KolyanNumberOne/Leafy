package com.example.leafy.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Taxonomy(
    val `class`: String,
    val genus: String,
    val order: String,
    val family: String,
    val phylum: String,
    val kingdom: String
)

@Serializable
data class Description(
    val value: String? = null,
    val citation: String? = null,
    @SerialName("license_name")
    val licenseName: String? = null,
    @SerialName("license_url")
    val licenseUrl: String? = null
)

@Serializable
data class Image(
    val value: String
)

@Serializable
data class Watering(
    val max: Int? = null,
    val min: Int? = null
)