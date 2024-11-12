package com.example.leafy.ui.screens.listplant

import com.example.leafy.data.local.database.Plant
import com.google.gson.Gson

fun toJson(plant: String): String {
    return Gson().toJson(plant)
}
fun fromJson(json: String): Plant {
    return Gson().fromJson(json, Plant::class.java)
}


//это все пока не используется