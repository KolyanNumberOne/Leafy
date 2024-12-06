package com.example.leafy.data.repository


import com.example.leafy.data.local.database.PlantDao
import com.example.leafy.data.models.PlantDetail
import com.example.leafy.data.remote.api.RemotePlantDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PlantRepository {

    val plants: Flow<List<PlantDetail>>

    //fun getAllPlants(): Flow<PagingData<PlantDetail>>

    suspend fun addPlant(plant: PlantDetail)

    suspend fun deletePlant(id: Int)

    suspend fun fetchPlantsByPage(page: Int): List<PlantDetail>

    suspend fun searchPlantsByName(name: String, page: Int): List<PlantDetail>

}

class PlantRepositoryImp @Inject constructor(
    private val plantDao: PlantDao,
    private val remotePlantDataSource: RemotePlantDataSource
) : PlantRepository {
//    override fun getAllPlants(): Flow<PagingData<PlantDetail>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 20,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { plantDao.getAllPlants() }
//        ).flow
//    }
    override val plants: Flow<List<PlantDetail>> =
        plantDao.getAllPlants()

    override suspend fun addPlant(plant: PlantDetail) = plantDao.insertPlant(plant)

    override suspend fun fetchPlantsByPage(page: Int): List<PlantDetail> = remotePlantDataSource.fetchPlantsByPage(page = page)

    override suspend fun searchPlantsByName(name: String, page: Int): List<PlantDetail> = remotePlantDataSource.searchPlantsByName(name = name, page = page)

    override suspend fun deletePlant(id: Int) = plantDao.deletePlantById(id = id)
}
