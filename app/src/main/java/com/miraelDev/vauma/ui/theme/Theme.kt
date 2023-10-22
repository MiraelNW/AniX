package com.miraelDev.vauma.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White

// dark/light green
private val LightMainColorPalette = lightColors(
    primary = Green,
    background = Color.White,
    onSurface = DarkWhite700,
    onBackground = Color.Black,
    onSecondary = White,
    secondaryVariant = Color(0xFFA29C9C)
)


private val DarkMainColorPalette = darkColors(
    primary = DarkGreen,
    background = Black,
    onSurface = LightBlack,
    onBackground = Color.White,
    onSecondary = LightBlack,
    secondaryVariant = Color(0xFF818181)
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HikariTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    MaterialTheme(
        colors = if (darkTheme) DarkMainColorPalette else LightMainColorPalette,
        shapes = Shapes,
        typography = Typography
    ) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null,
            content = content
        )
    }
}
