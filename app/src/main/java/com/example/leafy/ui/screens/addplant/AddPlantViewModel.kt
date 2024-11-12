package com.example.leafy.ui.screens.addplant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafy.data.local.database.Plant
import com.example.leafy.data.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository
): ViewModel() {

    fun addPlant(newPlant: Plant) {
        viewModelScope.launch {
            plantRepository.addPlant(newPlant)
        }
    }

}