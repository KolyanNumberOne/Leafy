package com.example.leafy.ui.screens.listplant

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafy.data.models.PlantDetail
import com.example.leafy.data.repository.PlantRepository
import com.example.leafy.data.repository.SharedPhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.leafy.data.NotificationReceiver
import com.example.leafy.data.models.PlantNotification
import kotlinx.coroutines.withTimeout
import java.util.Calendar


@HiltViewModel
class PlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
    private val sharedPhotoRepository: SharedPhotoRepository,
): ViewModel() {
    private val _allPlants = plantRepository.plants.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )
    val allPlants: StateFlow<List<PlantDetail>> = _allPlants

    val searchText = mutableStateOf("")
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex

    private val _searchList = MutableStateFlow<List<PlantDetail>>(emptyList())
    val searchList: StateFlow<List<PlantDetail>> = _searchList

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    private val _showToast = MutableStateFlow(false)
    val showToast: StateFlow<Boolean> = _showToast

    val sharedData = sharedPhotoRepository.sharedData

    private val _plantNotification = MutableStateFlow<List<PlantNotification>>(emptyList())
    val plantNotification: StateFlow<List<PlantNotification>> = _plantNotification

    init {
        searchPlants(name = "", page = 1)
        observeSharedData()
    }

    fun getNotificationsForPlant(plantId: Int){
        viewModelScope.launch {
            _plantNotification.value = plantRepository.getNotificationsForPlant(plantId = plantId)
        }
    }

    fun updateSelectedTabIndex(newIndex: Int) {
        _selectedTabIndex.value = newIndex
    }

    fun updateSearchText(newText: String) {
        searchText.value = newText
    }

    fun showToastState() {
        _showToast.value = true
    }

    fun resetToastState() {
        _showToast.value = false
    }
    private fun observeSharedData() {
        viewModelScope.launch {
            sharedData.collect { newData ->
                try {
                    Log.d("поиск", newData)
                    val content = plantRepository.imageSearch(image = newData)
                    Log.d("ФОТО", content.result.classification.suggestions[0].name)
                    val name = content.result.classification.suggestions[0].name
                    val prob = content.result.classification.suggestions[0].probability
                    if (prob > 0.3){
                        updateSelectedTabIndex(1)
                        val res = plantRepository.sendMessage(token = "Bearer ${plantRepository.authentication().accessToken}",content = name)
                        updateSearchText(res.choices[0].message.content)
                        searchPlants(name = res.choices[0].message.content, page = 1)
                    }
                    else{
                        showToastState()
                    }
                } catch (e: Exception) {
                    Log.e("Search Plant", "Ошибка при поиске растений по фото: ${e.message}")
                }
            }
        }
    }


    fun searchPlants(name: String, page: Int){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                withTimeout(3000) {
                    if (page == 1) {
                        clearSearchList()
                    }
                    if (name.isBlank()) {
                        val response = plantRepository.fetchPlantsByPage(page = page)
                        _searchList.value += response
                    } else {
                        val response = plantRepository.searchPlantsByName(name = name, page = page)
                        _searchList.value += response
                    }
                }
            } catch (e: Exception) {
                Log.e("Search Plant", "Ошибка при поиске растений: ${e.message}")
                _searchList.value = emptyList()
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun getPlantByName(plantName: String): PlantDetail? {
        val localPlant = allPlants.value.firstOrNull { plant ->
            plant.commonNames.contains(plantName)
        }

        if (localPlant != null) {
            return localPlant
        }

        return searchList.value.first { plantDetail ->
            plantDetail.commonNames.contains(plantName)
        }
    }

    fun addPlant(newPlant: PlantDetail) {
        viewModelScope.launch {
            plantRepository.addPlant(newPlant)
        }
    }

    fun deletePlant(deletePlant: PlantDetail) {
        viewModelScope.launch {
            plantRepository.deletePlant(deletePlant.id)
        }
    }

    fun clearSearchList() {
        _searchList.value = emptyList()
    }


    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotificationForPlant(context: Context, plant: PlantDetail, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        val notificationTime = calendar.timeInMillis
        val plantNotification = PlantNotification(
            plantId = plant.id,
            notificationTime = notificationTime,
            message = "Время для ухода: ${plant.commonNames[0]}"
        )


        viewModelScope.launch {
            val insertedId = plantRepository.insertNotification(plantNotification)
            val insertedNotification = plantRepository.getNotificationById(insertedId.toInt())
            scheduleNotification(context, insertedNotification)
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(context: Context, plantNotification: PlantNotification) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("message", plantNotification.message)
        }


        val pendingIntent = PendingIntent.getBroadcast(
            context,
            plantNotification.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            plantNotification.notificationTime,
            pendingIntent
        )
    }
    fun cancelNotification(context: Context, plantId: Int, notificationId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)

        viewModelScope.launch {
            plantRepository.deleteNotification(notificationId)
            getNotificationsForPlant(plantId = plantId)
        }
    }
}