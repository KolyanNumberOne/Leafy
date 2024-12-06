package com.example.leafy.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "plant_details")
@Serializable
data class PlantDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerialName("common_names")
    val commonNames: List<String>,
    val taxonomy: Taxonomy,
    val url: String? = null,
    @SerialName("gbif_id")
    val gbifId: Long? = null,
    @SerialName("inaturalist_id")
    val inaturalistId: Long? = null,
    val rank: String? = null,
    val description: Description,
    val image: Image,
    val images: List<Image>? = null,
    @SerialName("edible_parts")
    val edibleParts: List<String>? = null,
    val watering: Watering? = null,
    @SerialName("name_authority")
    val nameAuthority: String? = null,
    @SerialName("propagation_methods")
    val propagationMethods: List<String>? = null,
    @SerialName("best_light_condition")
    val bestLightCondition: String? = null,
    @SerialName("best_soil_type")
    val bestSoilType: String? = null,
    @SerialName("common_uses")
    val commonUses: String? = null,
    @SerialName("cultural_significance")
    val culturalSignificance: String? = null,
    val toxicity: String? = null,
    @SerialName("best_watering")
    val bestWatering: String? = null,
)



