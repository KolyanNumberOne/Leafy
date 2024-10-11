package com.example.leafy.data.di

import com.example.leafy.data.local.repository.PlantRepository
import com.example.leafy.data.local.repository.PlantRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsPlantRepository(
        plantRepository: PlantRepositoryImp
    ): PlantRepository
}