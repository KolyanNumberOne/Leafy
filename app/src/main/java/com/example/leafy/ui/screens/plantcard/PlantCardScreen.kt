package com.example.leafy.ui.screens.plantcard
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.leafy.R
import com.example.leafy.data.local.database.Plant
import com.example.leafy.ui.screens.listplant.PlantViewModel


@Composable
fun PlantCardScreen(plantName: String) {
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded3 by remember { mutableStateOf(false) }// Состояние для отслеживания развернутого текста

    val transition = updateTransition(targetState = expanded, label = "Text Expansion")
    val textSize by transition.animateDp(
        label = "Text Size",
        transitionSpec = { tween(durationMillis = 300) }
    ) { state ->
        if (state)  // Если текст развернут, увеличиваем размер
            200.dp
        else
            100.dp  // Если текст свернут, уменьшаем размер
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
             bitmap = ImageBitmap.imageResource(R.drawable.img), // URL изображения растени
             contentDescription = "Name",
             modifier = Modifier
                 .size(150.dp) // Размер изображения
                 .clip(CircleShape)
                 .align(Alignment.CenterHorizontally),// Фон для изображения
             contentScale = ContentScale.Crop

         )

        Spacer(modifier = Modifier.height(16.dp))


        Text("Название: $plantName ")
        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            expanded = !expanded },
            Modifier.fillMaxWidth()) {  // Изменяем состояние при нажатии на кнопку
            Text("Описание")
        }

        Spacer(modifier = Modifier.height(12.dp))
        AnimatedVisibility(
            visible = expanded,  // Показывать, если текст развернут
            enter = fadeIn() + expandVertically(),  // Анимация появления
            exit = fadeOut() + shrinkVertically()  // Анимация скрытия
        ) {
                // Текст с разворачиванием
            Text(
                text = "блалбалбалбалабалбалаблабалаблабалаблабалбалабалбалбалабалбалабалбалаблабалба.",
                maxLines = if (expanded) Int.MAX_VALUE else 1,  // В зависимости от состояния, количество строк будет меняться
                )}
        Button(onClick = {
            expanded2 = !expanded2 },
            Modifier.fillMaxWidth()) {  // Изменяем состояние при нажатии на кнопку
            Text("Семейство")
        }

        Spacer(modifier = Modifier.height(12.dp))
        AnimatedVisibility(
            visible = expanded2,  // Показывать, если текст развернут
            enter = fadeIn() + expandVertically(),  // Анимация появления
            exit = fadeOut() + shrinkVertically()  // Анимация скрытия
        ) {
                // Текст с разворачиванием
            Text(
                text = "блалбалбалбалабалбалаблабалаблабалаблабалбалабалбалбалабалбалабалбалаблабалба" +
                        "блалбалбалбалабалбалаблабалаблабалаблабалбалабалбалбалабалбалабалбалаблабалба" +
                        "блалбалбалбалабалбалаблабалаблабалаблабалбалабалбалбалабалбалабалбалаблабалба.",
                maxLines = if (expanded2) Int.MAX_VALUE else 1,  // В зависимости от состояния, количество строк будет меняться
                )}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                // Image(
                //     bitmap = ImageBitmap.imageResource(plant.id), // URL изображения растени
                //     contentDescription = "Name",
                //     modifier = Modifier
                //         .size(56.dp) // Размер изображения
                //         .clip(CircleShape),// Фон для изображения
                //     contentScale = ContentScale.Crop
                // )
        }


    }

    //}
    /*else{ Text("") }*/
}