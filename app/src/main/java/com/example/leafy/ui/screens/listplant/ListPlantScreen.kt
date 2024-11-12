package com.example.leafy.ui.screens.listplant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.leafy.R


/*@Preview(showBackground = true)
@Composable
fun PreviewListPlantScreen(){
    PlantGuideTheme {
        ListPlantScreen()
    }
}*/

@Composable
fun ListPlantScreen(navController: NavController){
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

        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)) {

            //items(listPlant.itemCount){ index ->
                //listPlant[index]?.let { Text(it.name) }
           //}
            items(listPlant.size){ index ->
                PlantItem(plantName = listPlant[index],navController = navController)
            }
        }

        Button(onClick = { navController.navigate("addplant")},
            shape = CircleShape,
            modifier = Modifier.align(Alignment.End)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("+", color = Color(0xFF2E7D32), fontSize = 24.sp)
        }
    }
}


@Composable
fun PlantItem(/*plant: Plant*/plantName: String, navController: NavController) {
    //val plantJson = toJson(plantName)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable( onClick = {navController.navigate("plantcard/$plantName")}),

        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Изображение растения
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.img),
                contentDescription = "Имя",
                modifier = Modifier
                    .size(56.dp) // Размер изображения
                    .clip(CircleShape),// Фон для изображения
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {

                Text(plantName, style = MaterialTheme.typography.titleMedium)
                Text("Вид: ")

            }
        }

    }
}

