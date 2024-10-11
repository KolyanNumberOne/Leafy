package com.example.leafy.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Plant::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao

}