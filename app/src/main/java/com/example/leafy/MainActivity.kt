package com.example.leafy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.leafy.ui.theme.PlantGuideTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantGuideTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    PlantListScreen()
                }
            }
        }
    }
}

@Composable
fun PlantListScreen(plantViewModel: PlantViewModel = viewModel()) {
    val plants by plantViewModel.allPlants.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

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
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Добавить растение")
        }
    }
    if (showDialog) {
        AddPlantScreen(onPlantAdded = { newPlant ->
            plantViewModel.addPlant(newPlant)
            showDialog = false
        })
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

@Preview(showBackground = true)
@Composable
fun pre(){
    PlantListScreen()
}