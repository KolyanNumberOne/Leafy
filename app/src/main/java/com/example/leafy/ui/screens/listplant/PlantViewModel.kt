package com.example.leafy.ui.screens.listplant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.leafy.data.local.database.Plant
import com.example.leafy.data.local.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class PlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository
): ViewModel() {

    val allPlants: Flow<PagingData<Plant>> = plantRepository.getAllPlants()
        .cachedIn(viewModelScope)

}