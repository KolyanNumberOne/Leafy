package com.example.leafy.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.leafy.data.models.PlantDetail
import com.example.leafy.data.utilities.Converters

@Database(entities = [PlantDetail::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao

}