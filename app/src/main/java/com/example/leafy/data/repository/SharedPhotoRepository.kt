package com.example.leafy.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface SharedPhotoRepository {
    val sharedData: StateFlow<String>

    fun updateData(newData: String)
}
class SharedPhotoRepositoryImp @Inject constructor(): SharedPhotoRepository {

    private val _sharedData = MutableStateFlow("")
    override val sharedData: StateFlow<String> = _sharedData

    override fun updateData(newData: String) {
        _sharedData.value = newData
    }
}