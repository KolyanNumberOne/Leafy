package com.example.leafy.ui.screens.listplant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.leafy.data.local.database.Plant
import com.example.leafy.ui.theme.PlantGuideTheme


@Preview(showBackground = true)
@Composable
fun PreviewListPlantScreen(){
    PlantGuideTheme {
        ListPlantScreen()
    }
}

@Composable
fun ListPlantScreen(){
    Column(modifier = Modifier
        //.fillMaxSize()
        .background(MaterialTheme.colorScheme.primary)){

        var checked by remember { mutableStateOf(true) }

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End){
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                    uncheckedThumbColor = Color.Red
                )
            )
        }


        var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
        val titles = listOf("Мои растения", "Все растения ")

        TabRow(selectedTabIndex = selectedTabIndex) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title) },
                )
            }
        }

        //val listPlant = plantViewModel.allPlants.collectAsLazyPagingItems()
        val listPlant = listOf("имя1", "имя2", "имя3", "имя4", "имя5", "имя6")
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
//            items(listPlant.itemCount){ index ->
//                listPlant[index]?.let { Text(it.name) }
//            }
            items(listPlant.size){ index ->
                Text(listPlant[index])
            }
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
        Column(modifier = Modifier.padding(16.dp).clickable {  }) {
            Text(plant.name, style = MaterialTheme.typography.titleMedium)
            Text("Освещение: ${plant.light}")
            Text("Полив раз в ${plant.wateringFrequency} дней")
        }
    }
}

