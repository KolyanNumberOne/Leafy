package com.example.leafy.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.leafy.ui.screens.addplant.AddPlantScreen
import com.example.leafy.ui.screens.listplant.ListPlantScreen
import com.example.leafy.ui.screens.listplant.fromJson
import com.example.leafy.ui.screens.plantcard.PlantCardScreen

@Composable
fun Navigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "listplant"){
        composable("listplant")  { ListPlantScreen(navController = navController) }
        composable("addplant")  { AddPlantScreen(navController = navController) }
        composable("plantcard/{plantName}") { backStackEntry ->
            val plantName = backStackEntry.arguments?.getString("plantName")
            PlantCardScreen(plantName = plantName ?: "")
        }
            /*"plantcard/{plant}",
            arguments = listOf(navArgument("plant") { type = NavType.StringType })  // Мы передаем строку JSON
        ) { backStackEntry ->
            val plantJson = backStackEntry.arguments?.getString("plant")
            val plantName = plantJson?.let { fromJson(it) }  // Десериализация
            PlantCardScreen(plant = plantName)
        }*/
    }
}