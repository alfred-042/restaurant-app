package com.alfred.restoranapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryOrange,
    secondary = SoftOrange,
    tertiary = BorderColor,
    background = DarkText,
    surface = DarkText,
    onPrimary = CardWhite,
    onSecondary = DarkText,
    onBackground = CreamBackground,
    onSurface = CreamBackground
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryOrange,
    secondary = SoftOrange,
    tertiary = BorderColor,
    background = CreamBackground,
    surface = CardWhite,
    onPrimary = CardWhite,
    onSecondary = DarkText,
    onBackground = DarkText,
    onSurface = DarkText,
    surfaceVariant = LightCream,
    onSurfaceVariant = DarkText,
    outline = BorderColor
)

@Composable
fun RestoranAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set to false to prioritize our brand colors over dynamic system colors
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