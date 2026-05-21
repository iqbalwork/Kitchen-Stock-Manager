package com.iqbalfauzi.kitchenstockmanager.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Background,
    surface = Surface,
    onBackground = OnBackground,
    onSurface = OnSurface,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = Color.Black,
    secondary = Secondary,
    tertiary = Tertiary,
    background = BackgroundDark,
    surface = SurfaceDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
