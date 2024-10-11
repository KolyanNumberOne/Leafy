package com.example.leafy.data.local.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.leafy.data.local.database.Plant
import com.example.leafy.data.local.database.PlantDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PlantRepository {
    fun getAllPlants(): Flow<PagingData<Plant>>

    suspend fun addPlant(plant: Plant)
}

class PlantRepositoryImp @Inject constructor(
    private val plantDao: PlantDao
) : PlantRepository {
    override fun getAllPlants(): Flow<PagingData<Plant>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { plantDao.getAllPlants() }
        ).flow
    }

    override suspend fun addPlant(plant: Plant) = plantDao.insertPlant(plant)
}
