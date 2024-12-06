package com.example.leafy.ui.screens.camera

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafy.data.repository.SharedPhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val sharedPhotoRepository: SharedPhotoRepository,
) : ViewModel() {

    private val _capturedImageUri = mutableStateOf<Uri?>(null)
    val capturedImageUri: State<Uri?> = _capturedImageUri

    private val _imageBase64 = mutableStateOf<String>("")
    val imageBase64: State<String> = _imageBase64

    private val _isProcessingImage = mutableStateOf(false)
    val isProcessingImage: State<Boolean> = _isProcessingImage

    fun capturePhoto(context: Context, imageCapture: ImageCapture, onBase64Ready: () -> Unit) {
        val outputDirectory = context.filesDir
        val photoFile = File(outputDirectory, "${System.currentTimeMillis()}.jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(outputOptions, Dispatchers.IO.asExecutor(), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val photoUri = Uri.fromFile(photoFile)
                _capturedImageUri.value = photoUri

                viewModelScope.launch {
                    _isProcessingImage.value = true
                    val base64String = convertUriToBase64(context, photoUri)
                    if (base64String.isNotEmpty()) {
                        _imageBase64.value = base64String
                        sharedPhotoRepository.updateData(newData = base64String)
                        onBase64Ready()
                    } else {
                        Log.e("Image", "Ошибка при конвертации фото в Base64")
                    }
                }
                _isProcessingImage.value = false
            }

            override fun onError(exception: ImageCaptureException) {
                exception.printStackTrace()
            }
        })
    }

    fun selectImageFromGallery(context: Context, uri: Uri, onBase64Ready: () -> Unit) {
        _capturedImageUri.value = uri

        viewModelScope.launch {
            _isProcessingImage.value = true
            val base64String = convertUriToBase64(context, uri)
            if (base64String.isNotEmpty()) {
                _imageBase64.value = base64String
                sharedPhotoRepository.updateData(newData = base64String)
                onBase64Ready()
            } else {
                Log.e("Image", "Ошибка при конвертации галерейного изображения в Base64")
            }
        }
        _isProcessingImage.value = false
    }

    private suspend fun convertUriToBase64(context: Context, uri: Uri): String {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            if (inputStream == null) {
                Log.e("convertUriToBase64", "Не удалось открыть InputStream для Uri: $uri")
                return ""
            }
            val byteArray = inputStream.use { it.readBytes() }
            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            Log.e("convertUriToBase64", "Ошибка при конвертации Uri в Base64: $uri", e)
            ""
        }
    }
}


