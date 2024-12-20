package com.example.leafy.data.repository


import com.example.leafy.data.local.database.PlantDao
import com.example.leafy.data.models.ChatRequest
import com.example.leafy.data.models.ChatResponse
import com.example.leafy.data.models.ImageSearchRequest
import com.example.leafy.data.models.Message
import com.example.leafy.data.models.PlantDetail
import com.example.leafy.data.models.PlantResponse
import com.example.leafy.data.remote.api.OpenAIApiDataSource
import com.example.leafy.data.remote.api.PlantApiDataSource
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

    suspend fun imageSearch(image: String): PlantResponse

    suspend fun sendMessage(content: String): ChatResponse

}

class PlantRepositoryImp @Inject constructor(
    private val plantDao: PlantDao,
    private val remotePlantDataSource: RemotePlantDataSource,
    private val plantApiDataSource: PlantApiDataSource,
    private val openAIApiDataSource: OpenAIApiDataSource
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

    override suspend fun imageSearch(image: String): PlantResponse = plantApiDataSource.imageSearch(requestBody = ImageSearchRequest(images = listOf(image)))

    override suspend fun sendMessage(content: String): ChatResponse = openAIApiDataSource
        .sendMessage(request = ChatRequest(
            model = "GigaChat",
            stream = false,
            update_interval = 0,
            messages = listOf(
                Message(role = "system", content = "В ответе напиши только основное название растения, одно слово. Переведи название растения на русский язык: ${content}.")
            )))
}
