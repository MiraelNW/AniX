package com.miraeldev.theme

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration

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


val LocalOrientation = compositionLocalOf { Configuration.ORIENTATION_PORTRAIT }


val LocalTheme = compositionLocalOf<Boolean> { false }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HikariTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    var orientation by remember { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }


    MaterialTheme(
        colors = if (darkTheme) DarkMainColorPalette else LightMainColorPalette,
        shapes = Shapes,
        typography = Typography
    ) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null,
            LocalOrientation provides orientation,
            LocalTheme provides darkTheme,
            content = content
        )
    }
}
