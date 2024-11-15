package com.example.leafy.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlantDetail(
    @SerialName("common_names")
    val commonNames: List<String>,
    val taxonomy: Taxonomy,
    val url: String,
    @SerialName("gbif_id")
    val gbifId: Long,
    @SerialName("inaturalist_id")
    val inaturalistId: Long,
    val rank: String,
    val description: Description,
    val synonyms: List<String>,
    val image: Image,
    val images: List<Image>,
    @SerialName("edible_parts")
    val edible_parts: List<String>? = null,
    val watering: Watering,
    @SerialName("name_authority")
    val nameAuthority: String,
    @SerialName("propagation_methods")
    val propagationMethods: String? = null,
    @SerialName("best_light_condition")
    val bestLightCondition: String,
    @SerialName("best_soil_type")
    val bestSoilType: String,
    @SerialName("common_uses")
    val commonUses: String,
    @SerialName("cultural_significance")
    val culturalSignificance: String,
    val toxicity: String,
    @SerialName("best_watering")
    val bestWatering: String,
)

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
    val value: String,
    val citation: String,
    @SerialName("license_name")
    val licenseName: String,
    @SerialName("license_url")
    val licenseUrl: String
)

@Serializable
data class Image(
    val value: String
)

@Serializable
data class Watering(
    val max: Int,
    val min: Int
)