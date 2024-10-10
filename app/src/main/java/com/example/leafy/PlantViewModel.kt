package com.example.leafy

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlantViewModel : ViewModel() {
    private val _allPlants = MutableStateFlow(listOf<Plant>())
    val allPlants: StateFlow<List<Plant>> = _allPlants.asStateFlow()

    fun addPlant(newPlant: Plant) {

        _allPlants.value = _allPlants.value + newPlant
    }
}