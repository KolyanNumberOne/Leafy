//package com.example.leafy.ui.screens.addplant
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavController
//import com.example.leafy.data.local.database.Plant
//import com.example.leafy.ui.theme.PlantGuideTheme
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewPlantScreen(){
//    PlantGuideTheme {
//    }
//}
//
//@Composable
//fun AddPlantScreen(addPlantViewModel: AddPlantViewModel = hiltViewModel(), navController: NavController) {
//    var name by remember { mutableStateOf("") }
//    var light by remember { mutableStateOf("") }
//    var wateringFrequency by remember { mutableStateOf("7") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text("Добавить новое растение", style = MaterialTheme.typography.bodyLarge)
//
//        Spacer(modifier = Modifier.height(16.dp))
//        TextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Название растения") }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//        TextField(
//            value = light,
//            onValueChange = { light = it },
//            label = { Text("Освещение") }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//        TextField(
//            value = wateringFrequency,
//            onValueChange = { wateringFrequency = it },
//            label = { Text("Частота полива (дни)") }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = {
//            if (name.isNotBlank() && light.isNotBlank() && wateringFrequency.isNotBlank()) {
//                val newPlant = Plant(name, light, wateringFrequency.toInt())
//                addPlantViewModel.addPlant(newPlant)
//                navController.navigate("listplant")
//            }
//        }) {
//            Text("Сохранить")
//        }
//    }
//}
//
//
//
