package com.example.leafy.data.di

import com.example.leafy.data.repository.PlantRepository
import com.example.leafy.data.repository.PlantRepositoryImp
import com.example.leafy.data.repository.SharedPhotoRepository
import com.example.leafy.data.repository.SharedPhotoRepositoryImp
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

    @Singleton
    @Binds
    fun bindsSharedPhotoRepository(
        sharedPhotoRepository: SharedPhotoRepositoryImp
    ): SharedPhotoRepository

}