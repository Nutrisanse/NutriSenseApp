package com.example.nutrisense.ui.theme

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.nutrisense.ui.theme.AppTypography
import com.example.nutrisense.ui.theme.Blue10
import com.example.nutrisense.ui.theme.Blue40
import com.example.nutrisense.ui.theme.Blue90
import com.example.nutrisense.ui.theme.DarkPurpleGray10
import com.example.nutrisense.ui.theme.Orange10
import com.example.nutrisense.ui.theme.Orange40
import com.example.nutrisense.ui.theme.Orange90
import com.example.nutrisense.ui.theme.Purple10
import com.example.nutrisense.ui.theme.Purple90
import com.example.nutrisense.ui.theme.PurpleGray30
import com.example.nutrisense.ui.theme.PurpleGray50
import com.example.nutrisense.ui.theme.PurpleGray90
import com.example.nutrisense.ui.theme.Red10
import com.example.nutrisense.ui.theme.Red40
import com.example.nutrisense.ui.theme.Red90

/**
 * Light default theme color scheme
 */
@VisibleForTesting
val LightColors = lightColorScheme(
    primary = Color(0xff0ea5e9),
    onPrimary = Color.White,
    primaryContainer = Purple90,
    onPrimaryContainer = Purple10,
    secondary = Orange40,
    onSecondary = Color.White,
    secondaryContainer = Orange90,
    onSecondaryContainer = Orange10,
    tertiary = Blue40,
    onTertiary = Color.White,
    tertiaryContainer = Blue90,
    onTertiaryContainer = Blue10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Color.White,
    onBackground = DarkPurpleGray10,
    surface = Color.White,
    onSurface = DarkPurpleGray10,
    surfaceVariant = PurpleGray90,
    onSurfaceVariant = PurpleGray30,
    outline = PurpleGray50
)


@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
//    val colors = if (!useDarkTheme) {
//        LightColors
//    } else {
//        DarkColors
//    }


    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        content = content
    )
}