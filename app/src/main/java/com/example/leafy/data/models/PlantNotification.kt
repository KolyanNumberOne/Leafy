package com.example.leafy.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(
    tableName = "plant_notifications",
    foreignKeys = [
        ForeignKey(
            entity = PlantDetail::class,
            parentColumns = ["id"],
            childColumns = ["plantId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Serializable
data class PlantNotification(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerialName("plant_id")
    val plantId: Int,

    @SerialName("notification_time")
    val notificationTime: Long,

    @SerialName("message")
    val message: String? = null
)