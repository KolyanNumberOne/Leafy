package com.example.leafy.ui.screens.listplant

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.platform.LocalContext


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
    val searchText = mutableStateOf("")
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex
//    val searchText: StateFlow<String> = _searchText

    val allPlants: StateFlow<List<PlantDetail>> = _allPlants
//    val allPlants: Flow<PagingData<PlantDetail>> = plantRepository.getAllPlants()
//        .cachedIn(viewModelScope)
    private val _searchList = MutableStateFlow<List<PlantDetail>>(emptyList())
    val searchList: StateFlow<List<PlantDetail>> = _searchList

    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading.value

    private val _probability = mutableStateOf(0f)
    val probability: State<Float> = _probability


    val sharedData = sharedPhotoRepository.sharedData

    init {
        searchPlants(name = "", page = 1)
        observeSharedData()
        Log.d("ALLPLANT", "${allPlants}")

    }

    fun updateSelectedTabIndex(newIndex: Int) {
        _selectedTabIndex.value = newIndex
    }

    fun updateSearchText(newText: String) {
        searchText.value = newText
    }

    fun updateProbability(newProbability: Float) {
        _probability.value = newProbability
    }
    private fun observeSharedData() {
        viewModelScope.launch {
            sharedData.collect { newData ->
                try {
                    Log.d("поиск", newData)
                    val content = plantRepository.imageSearch(image = newData)
                    Log.d("ФОТО", content.result.classification.suggestions[0].name)
                    val name = content.result.classification.suggestions[0].name
                    val probability = content.result.classification.suggestions[0].probability
                    updateProbability(probability)
                    if (probability > 0.3){
                        val res = plantRepository.sendMessage(content = name)
                        updateSearchText(res.choices[0].message.content)
                        searchPlants(name = res.choices[0].message.content, page = 1)
                        updateSelectedTabIndex(1)
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
}