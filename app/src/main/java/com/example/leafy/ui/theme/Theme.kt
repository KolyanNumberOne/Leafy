package com.example.leafy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColorPalette = lightColorScheme(
    primary = Color(0xFF1B931F),
    secondary = Color(0xFF80CBC4),
    background = Color(0xFFB6E79A),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFF2E7D32),
    onSurface = Color(0xFF000000)
)

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFFA5D6A7),
    secondary = Color(0xFF80CBC4),
    background = Color(0xFF5965A1),
    surface = Color(0xFF424242),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
    onBackground = Color(0xFFC8E6C9),
    onSurface = Color(0xFFFFFFFF)
)

private val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)


@Composable
fun PlantGuideTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}