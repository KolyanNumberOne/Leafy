package com.example.leafy.data.utilities

import androidx.room.TypeConverter
import com.example.leafy.data.models.Description
import com.example.leafy.data.models.Image
import com.example.leafy.data.models.Taxonomy
import com.example.leafy.data.models.Watering
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let { Json.decodeFromString(it) }
    }

    @TypeConverter
    fun fromImageList(value: List<Image>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toImageList(value: String?): List<Image>? {
        return value?.let { Json.decodeFromString(it) }
    }

    @TypeConverter
    fun fromTaxonomy(taxonomy: Taxonomy): String {
        return Json.encodeToString(taxonomy)
    }

    @TypeConverter
    fun toTaxonomy(value: String): Taxonomy {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromDescription(description: Description): String {
        return Json.encodeToString(description)
    }

    @TypeConverter
    fun toDescription(value: String): Description {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromWatering(watering: Watering?): String? {
        return watering?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toWatering(value: String?): Watering? {
        return value?.let { Json.decodeFromString(it) }
    }

    @TypeConverter
    fun fromImage(image: Image): String {
        return Json.encodeToString(image)
    }

    @TypeConverter
    fun toImage(value: String): Image {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromNullableString(value: String?): String? {
        return value
    }

    @TypeConverter
    fun fromNullableLong(value: Long?): Long? {
        return value
    }

    @TypeConverter
    fun fromNullableInt(value: Int?): Int? {
        return value
    }

}
