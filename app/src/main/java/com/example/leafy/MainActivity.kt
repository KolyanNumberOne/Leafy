package com.example.leafy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leafy.ui.theme.PlantGuideTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantGuideTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation(plantViewModel: PlantViewModel = viewModel()){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainPage") {
        composable("mainPage") { PlantListScreen(plantViewModel, navController) }
        composable("addPlant") { AddPlantScreen({ newPlant ->
            plantViewModel.addPlant(newPlant)
        }, navController) }
    }
}

@Composable
fun PlantListScreen(plantViewModel: PlantViewModel, navController: NavController) {
    val plants by plantViewModel.allPlants.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Ваши растения", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(plants) { plant ->
                PlantItem(plant = plant)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("addPlant") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Добавить растение")
        }
    }

}

@Composable
fun PlantItem(plant: Plant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(plant.name, style = MaterialTheme.typography.titleMedium)
            Text("Освещение: ${plant.light}")
            Text("Полив раз в ${plant.wateringFrequency} дней")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun pre(){
//    PlantListScreen()
//}