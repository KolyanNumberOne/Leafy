package com.example.leafy.ui.screens.listplant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.leafy.data.local.database.Plant
import com.example.leafy.data.models.PlantDetail
import com.example.leafy.data.models.SearchPlant
import com.example.leafy.data.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
): ViewModel() {

    val allPlants: Flow<PagingData<Plant>> = plantRepository.getAllPlants()
        .cachedIn(viewModelScope)

    private val _searchList = MutableStateFlow<List<SearchPlant>>(emptyList())
    val searchList: StateFlow<List<SearchPlant>> = _searchList

    private val _plantDetailList = MutableStateFlow<List<PlantDetail>>(emptyList())
    val plantDetailList: StateFlow<List<PlantDetail>> = _plantDetailList

    fun searchPlants(query: String){
        viewModelScope.launch {
            _plantDetailList.value = emptyList()
            try {
                if (query.isBlank()) {
                    _searchList.value = emptyList()
                } else {
                    val response = plantRepository.searchPlants(query = query)
                    _searchList.value = response.entities
                }
            } catch (e: Exception) {
                Log.e("Search Plant", "Ошибка при поиске растений: ${e.message}")
                _searchList.value = emptyList()
            }
            plantDetails()
        }
    }
    private suspend fun plantDetails(){
        try {
            searchList.value.forEach { searchPlant ->
                Log.d("acces", searchPlant.accessToken)
                val detail = plantRepository.plantDetail(searchPlant.accessToken)
                Log.d("detail", detail.toString())
                _plantDetailList.value += detail
            }
        } catch (e: Exception) {
            Log.e("Plant Detail", "Ошибка при получении информации о растении: ${e.message}")
        }
    }

    fun getCurrentPlant(commonName: String): PlantDetail {
        return plantDetailList.value.first { plantDetail ->
            plantDetail.commonNames.contains(commonName)
        }
    }


}