package com.example.leafy.ui.screens.listplant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.leafy.data.models.PlantDetail
import com.example.leafy.ui.theme.PlantGuideTheme


//@Preview(showBackground = true)
//@Composable
//fun PreviewListPlantScreen(){
//    PlantGuideTheme {
//        ListPlantScreen(navController = rememberNavController())
//    }
//}


@Composable
fun ListPlantScreen(plantViewModel: PlantViewModel, navController: NavController,isDarkTheme: Boolean,
                    onThemeToggle: (Boolean) -> Unit) {
    var checked by remember { mutableStateOf(true) }
    /*PlantGuideTheme(darkTheme = checked) {*/
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
        ) {

            var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
            val titles = listOf("Мои растения", "Все растения ")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "Leafy",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White
                )

                Switch(
                    modifier = Modifier.padding(end = 16.dp),
                    checked = isDarkTheme,
                    onCheckedChange = onThemeToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Green,
                        uncheckedThumbColor = Color.Red,
                        checkedBorderColor = Color.Black,
                        uncheckedBorderColor = Color.Black,
                        checkedTrackColor = Color.DarkGray,
                        uncheckedTrackColor = Color.White
                    )

                )
            }


            TabRow(selectedTabIndex = selectedTabIndex) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = title) },
                        unselectedContentColor = Color.LightGray,
                        selectedContentColor = Color.Green
                    )
                }
            }

            Spacer(modifier = Modifier.size(8.dp))

            if (selectedTabIndex == 0) MyPlants(
                plantViewModel,
                onTabChange = { selectedTabIndex = it },
                navController
            ) else AllPlants(plantViewModel, navController)

        }
    }


@Composable
fun PlantItem(plant: PlantDetail, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { navController.navigate("plantcard/${plant.commonNames[0]}") }),

        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, top = 8.dp, end = 24.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageWithLoading(plant.image.value)
            /*Image(
                painter = rememberAsyncImagePainter(plant.image.value),
                contentDescription = "Имя",
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentScale = ContentScale.Crop
            )*/

            Spacer(modifier = Modifier.size(16.dp))

            Column(modifier = Modifier.padding(16.dp)) {

                Text(plant.commonNames[0], style = MaterialTheme.typography.titleMedium)
                Text("Род:  ${plant.taxonomy.genus}")
                Text("Семейство:  ${plant.taxonomy.family}")

            }
        }

    }
}
@Composable
fun ImageWithLoading(plantImage: String) {
    val painter = rememberAsyncImagePainter(
        model = plantImage,
        onState = { state ->
            when (state) {
                is AsyncImagePainter.State.Loading -> {
                    // Можно здесь добавить логику, если нужно
                }
                is AsyncImagePainter.State.Error -> {
                    // Логика на случай ошибки загрузки
                }
                else -> Unit
            }
        }
    )

    var isLoading by remember { mutableStateOf(true) }

    Box(modifier = Modifier.size(80.dp)) {
        Image(
            painter = painter,
            contentDescription = "Имя",
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentScale = ContentScale.Crop
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp),
                color = Color.Gray
            )
        }

        LaunchedEffect(painter.state) {
            isLoading = painter.state is AsyncImagePainter.State.Loading
        }
    }
}

@Composable
fun MyPlants(plantViewModel: PlantViewModel, onTabChange: (Int) -> Unit,  navController: NavController){
    Spacer(modifier = Modifier.height(8.dp))

    val listPlant = plantViewModel.allPlants.collectAsLazyPagingItems()

    var showAddPlantDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (listPlant.itemCount == 0){
                item { Text(
                    "У вас пока нет растений",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White)
                }
            }
            else{
                items(listPlant.itemCount) { index ->
                    //PlantItem(plant = listPlant[index], navController = navController)
                }
            }
        }
        Button(
            onClick = { showAddPlantDialog = true },
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("+", color = Color.Black, fontSize = 24.sp)
        }
    }
    if (showAddPlantDialog) {
        AlertDialog(
            onDismissRequest = { showAddPlantDialog = false },
            title = { Text("Добавить растение") },
            text = { Text("Выберите, как вы хотите добавить растение:") },
            confirmButton = {
                TextButton(onClick = {
                    showAddPlantDialog = false
                    //navController.navigate("addplant_photo")
                }) {
                    Text("По фото", color = Color(0xFF4BA34F))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showAddPlantDialog = false
                    onTabChange(1)
                }) {
                    Text("Поиск", color = Color(0xFF4BA34F))
                }
            }
        )
    }
}
@Composable
fun AllPlants(plantViewModel: PlantViewModel, navController: NavController){
    var searchText by remember { mutableStateOf("") }
    val listPlant = plantViewModel.plantDetailList.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.Green),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = searchText,
            onValueChange = {searchText=it},
            placeholder = { Text("Поиск растений") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                unfocusedTextColor = Color(0xFF4CAF50),
                focusedContainerColor = Color.Transparent,
                focusedTextColor = Color(0xff222222)
            ),
        )
        Spacer(modifier = Modifier.size(8.dp))
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            modifier = Modifier
                .size(36.dp)
                .offset(x = (-12).dp)
                .clickable { plantViewModel.searchPlants(query = searchText) },
            tint = Color.White
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(listPlant.value.size) { index ->
            PlantItem(plant= listPlant.value[index], navController = navController)
        }
    }
}

