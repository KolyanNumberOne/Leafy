package com.example.leafy.ui.screens.listplant

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.leafy.data.local.database.Plant



@Preview(showBackground = true)
@Composable
fun PreviewListPlantScreen(){
//    ListPlantScreen()
}


@Composable
fun ListPlantScreen(plantViewModel: PlantViewModel = hiltViewModel(), navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    val plants = plantViewModel.allPlants.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ваши растения", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.height(600.dp)) {

            items(plants.itemSnapshotList.size) { index ->
                plants[index]?.let { PlantItem(plant = it) }
            }

            when {
                plants.loadState.refresh is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }
                plants.loadState.refresh is LoadState.Error -> {
                    item {
                        Text("Ошибка при загрузке растений")
                    }
                }
                plants.loadState.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("addplant")},
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
