package com.example.leafy.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.leafy.data.models.PlantNotification

@Dao
interface PlantNotificationDao {

    @Insert
    suspend fun insertPlantNotification(plantNotification: PlantNotification): Long

    @Query("SELECT * FROM plant_notifications WHERE plantId = :plantId")
    suspend fun getNotificationsForPlant(plantId: Int): List<PlantNotification>

    @Query("SELECT * FROM plant_notifications WHERE id = :notificationId")
    suspend fun getNotificationById(notificationId: Int): PlantNotification

    @Query("DELETE FROM plant_notifications WHERE plantId = :plantId")
    suspend fun deleteNotificationsForPlant(plantId: Int)

    @Query("DELETE FROM plant_notifications WHERE id = :notificationId")
    suspend fun deleteNotification(notificationId: Int)
}