package com.example.leafy.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query


@Entity(tableName = "plants")
data class Plant(
    val name: String,
    val light: String,
    val wateringFrequency: Int
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


@Dao
interface PlantDao {

    @Query("SELECT * FROM plants ORDER BY id ASC")
    fun getAllPlants(): PagingSource<Int, Plant>

    @Insert
    suspend fun insertPlant(plant: Plant)
}