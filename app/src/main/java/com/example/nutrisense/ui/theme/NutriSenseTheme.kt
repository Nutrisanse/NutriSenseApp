package com.example.nutrisense.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Shapes = Shapes()

// Define light and dark color schemes
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFE4773),
    onPrimary = Color.White,
    secondary = Color(0xFFFA9F4B),
    onSecondary = Color.White,
    background = Color(0xFFF6F6F6),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFE4773),
    onPrimary = Color.White,
    secondary = Color(0xFFFA9F4B),
    onSecondary = Color(0xFFF65696B),
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.DarkGray,
    onSurface = Color.White
)

@Composable
fun NutriSenseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
