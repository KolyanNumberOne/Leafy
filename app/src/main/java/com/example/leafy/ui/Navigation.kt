package com.example.leafy.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leafy.ui.screens.addplant.AddPlantScreen
import com.example.leafy.ui.screens.listplant.ListPlantScreen

@Composable
fun Navigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "listplant"){
        composable("listplant")  { ListPlantScreen(/*navController = navController*/) }
        composable("addplant")  { AddPlantScreen(navController = navController) }
    }
}