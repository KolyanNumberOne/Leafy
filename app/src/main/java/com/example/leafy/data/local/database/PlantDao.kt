package com.example.leafy.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.leafy.data.models.PlantDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Insert
    suspend fun insertPlant(plant: PlantDetail)

    @Query("SELECT * FROM plant_details")
    fun getAllPlants(): Flow<List<PlantDetail>>

    @Query("SELECT * FROM plant_details WHERE id = :id")
    suspend fun getPlantById(id: Int): PlantDetail

    @Query("DELETE FROM plant_details WHERE id = :id")
    suspend fun deletePlantById(id: Int)
}