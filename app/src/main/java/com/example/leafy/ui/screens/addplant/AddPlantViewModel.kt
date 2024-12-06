package com.example.leafy.ui.screens.addplant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafy.data.models.PlantDetail
import com.example.leafy.data.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository
): ViewModel() {

    fun addPlant(newPlant: PlantDetail) {
        viewModelScope.launch {
            plantRepository.addPlant(newPlant)
        }
    }

}