package com.example.leafy.data.utilities

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun String.asImageBitmap(): ImageBitmap {
    return ImageConverter.base64toBitmap(this)!!.asImageBitmap()
}