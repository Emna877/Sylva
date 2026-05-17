package com.example.sylva.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ForestDeep,
    onPrimary = Ivory,
    secondary = MossGreen,
    onSecondary = Ivory,
    tertiary = LeafGold,
    onTertiary = ForestNight,
    background = ForestNight,
    onBackground = Ivory,
    surface = SurfaceDark,
    onSurface = Ivory,
    surfaceVariant = SurfaceDarkElevated,
    onSurfaceVariant = MistGreen,
    outline = MistGreen
)

private val LightColorScheme = lightColorScheme(
    primary = ForestDeep,
    onPrimary = Ivory,
    secondary = MossGreen,
    onSecondary = Ivory,
    tertiary = LeafGold,
    onTertiary = ForestNight,
    background = Cream,
    onBackground = ForestNight,
    surface = SurfaceLight,
    onSurface = ForestNight,
    surfaceVariant = Color(0xFFE9E1D1),
    onSurfaceVariant = BarkBrown,
    outline = BarkBrown
)

@Composable
fun SylvaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}