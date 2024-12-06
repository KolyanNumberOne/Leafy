package com.example.leafy.ui.screens.plantcard
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.leafy.ui.screens.listplant.PlantViewModel

//@Preview(showBackground = true)
//@Composable
//fun PreviewPlantCardScreen() {
//    val plantName = "Aloe Vera"
//
//    PlantCardScreen(
//        plantName = plantName,
//        navController = rememberNavController()
//    )
//}

@Composable
fun PlantCardScreen(plantName: String, navController: NavController, plantViewModel: PlantViewModel) {
    val plant = plantViewModel.getPlantByName(plantName)

    if (plant == null) {
        Text(
            text = "Растение не найдено",
            modifier = Modifier.fillMaxSize(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Image(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .size(36.dp)
                    .clickable { navController.popBackStack() }
            )
        }
        Text(
            plant.commonNames[0],
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,

        )
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = rememberAsyncImagePainter(plant.image.value),
            contentDescription = "Name",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentScale = ContentScale.Crop

        )
        Spacer(modifier = Modifier.height(16.dp))

        if (!plant.description.value.isNullOrEmpty()) {
            GeneralInf(plant.description.value)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!plant.commonNames.isNullOrEmpty()) {
            ToggleView("Все имена", plant.commonNames.joinToString(", "))
            Spacer(modifier = Modifier.height(12.dp))
        }
        if (!plant.taxonomy.family.isNullOrBlank()) {
            ToggleView("Классификация", "Семейство: ${plant.taxonomy.family}\n" +
                    "Род: ${plant.taxonomy.genus}\n" +
                    "Тип: ${plant.taxonomy.phylum}\n" +
                    "Порядок: ${plant.taxonomy.order}\n" +
                    "Царство: ${plant.taxonomy.kingdom}\n"
                    )
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (!plant.commonUses.isNullOrBlank()){
            ToggleView("Использование", plant.commonUses )
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (!plant.culturalSignificance.isNullOrBlank()){
            ToggleView("Культурная значимость", plant.culturalSignificance )
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (!plant.edibleParts.isNullOrEmpty()){
            ToggleView("Съедобные части", plant.edibleParts.joinToString(", ") )
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (!plant.propagationMethods.isNullOrEmpty()){
            ToggleView("Методы распространения", plant.propagationMethods.joinToString(", "))
            Spacer(modifier = Modifier.height(12.dp))
        }


        if (!plant.bestWatering.isNullOrBlank()){
            ToggleView("Полив", plant.bestWatering )
            Spacer(modifier = Modifier.height(12.dp))
        }
        if (!plant.bestSoilType.isNullOrBlank()) {
            ToggleView("Почва", plant.bestSoilType)
            Spacer(modifier = Modifier.height(12.dp))
        }
        if (!plant.bestLightCondition.isNullOrBlank()) {
            ToggleView("Освещение", plant.bestLightCondition)
            Spacer(modifier = Modifier.height(12.dp))
        }




    }
}

@Composable
fun ToggleView(categoryName: String, body: String) {
    var isVisible by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable { isVisible = !isVisible },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                categoryName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp))

            Icon(
                imageVector = if (isVisible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Search Icon",
                modifier = Modifier
                    .size(36.dp)
                    .offset(x = (-12).dp),
                tint = Color.Black,
            )
        }


        if (isVisible) {
            Divider(
                color = Color.Gray.copy(alpha = 0.5f),
                thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                body,
                textAlign = TextAlign.Justify,
                fontSize = 16.sp,

                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp))
        }
    }
}

@Composable
fun GeneralInf(generalText: String){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Text(
            generalText,
            textAlign = TextAlign.Justify,
            fontSize = 14.sp,

        )
    }
}