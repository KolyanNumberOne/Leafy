package com.example.leafy.data.repository


import com.example.leafy.data.local.database.PlantDao
import com.example.leafy.data.local.database.PlantNotificationDao
import com.example.leafy.data.models.AuthResponse
import com.example.leafy.data.models.ChatRequest
import com.example.leafy.data.models.ChatResponse
import com.example.leafy.data.models.ImageSearchRequest
import com.example.leafy.data.models.Message
import com.example.leafy.data.models.PlantDetail
import com.example.leafy.data.models.PlantNotification
import com.example.leafy.data.models.PlantResponse
import com.example.leafy.data.remote.api.GigaChatApiDataSource
import com.example.leafy.data.remote.api.GigaChatAuth
import com.example.leafy.data.remote.api.PlantApiDataSource
import com.example.leafy.data.remote.api.RemotePlantDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PlantRepository {

    val plants: Flow<List<PlantDetail>>

    suspend fun getNotificationsForPlant(plantId: Int): List<PlantNotification>

    suspend fun deleteNotification(notificationId: Int)

    suspend fun addPlant(plant: PlantDetail)

    suspend fun deletePlant(id: Int)

    suspend fun fetchPlantsByPage(page: Int): List<PlantDetail>

    suspend fun searchPlantsByName(name: String, page: Int): List<PlantDetail>

    suspend fun imageSearch(image: String): PlantResponse

    suspend fun sendMessage(token: String, content: String): ChatResponse

    suspend fun insertNotification(notification: PlantNotification): Long

    suspend fun getNotificationById(insertedId: Int): PlantNotification

    suspend fun authentication(): AuthResponse
}

class PlantRepositoryImp @Inject constructor(
    private val plantDao: PlantDao,
    private val plantNotificationDao: PlantNotificationDao,
    private val remotePlantDataSource: RemotePlantDataSource,
    private val plantApiDataSource: PlantApiDataSource,
    private val openAIApiDataSource: GigaChatApiDataSource,
    private val gigaChatAuth: GigaChatAuth
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

    override suspend fun getNotificationsForPlant(plantId: Int): List<PlantNotification> = plantNotificationDao.getNotificationsForPlant(plantId = plantId)

    override suspend fun addPlant(plant: PlantDetail) = plantDao.insertPlant(plant)

    override suspend fun fetchPlantsByPage(page: Int): List<PlantDetail> = remotePlantDataSource.fetchPlantsByPage(page = page)

    override suspend fun searchPlantsByName(name: String, page: Int): List<PlantDetail> = remotePlantDataSource.searchPlantsByName(name = name, page = page)

    override suspend fun deletePlant(id: Int) = plantDao.deletePlantById(id = id)

    override suspend fun imageSearch(image: String): PlantResponse = plantApiDataSource.imageSearch(requestBody = ImageSearchRequest(images = listOf(image)))

    override suspend fun sendMessage(token: String, content: String): ChatResponse = openAIApiDataSource
        .sendMessage( token = token,
            request = ChatRequest(
                model = "GigaChat",
                stream = false,
                update_interval = 0,
                messages = listOf(
                    Message(role = "system", content = "Назови только одно основное название растения на русском языке, без дополнительных слов или описаний: ${content}.")
                )))

    override suspend fun insertNotification(plantNotification: PlantNotification): Long {
        return plantNotificationDao.insertPlantNotification(plantNotification)
    }
    override suspend fun deleteNotification(notificationId: Int) = plantNotificationDao.deleteNotification(notificationId = notificationId)

    override suspend fun getNotificationById(insertedId: Int): PlantNotification = plantNotificationDao.getNotificationById(notificationId = insertedId)

    override suspend fun authentication(): AuthResponse = gigaChatAuth.authentication()
}
