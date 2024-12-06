package com.example.leafy.ui

import CameraScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leafy.ui.screens.listplant.ListPlantScreen
import com.example.leafy.ui.screens.listplant.PlantViewModel
import com.example.leafy.ui.screens.plantcard.PlantCardScreen


@Composable
fun Navigation(onThemeToggle: (Boolean) -> Unit, isDarkTheme: Boolean){

    val navController = rememberNavController()
    val plantViewModel: PlantViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "listplant"){
        composable("listplant")  { ListPlantScreen(plantViewModel = plantViewModel, navController = navController, isDarkTheme = isDarkTheme, onThemeToggle = onThemeToggle) }
//        composable("addplant")  { AddPlantScreen(navController = navController) }
        composable("camera") { CameraScreen(navController = navController)}
        composable("plantcard/{plantName}") { backStackEntry ->
            val plantName = backStackEntry.arguments?.getString("plantName")
            PlantCardScreen(plantName = plantName ?: "", plantViewModel = plantViewModel, navController = navController)
        }
    }
}