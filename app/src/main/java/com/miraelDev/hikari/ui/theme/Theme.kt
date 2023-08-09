package com.guru.composecookbook.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.miraelDev.hikari.ui.theme.Black
import com.miraelDev.hikari.ui.theme.BlackDarker
import com.miraelDev.hikari.ui.theme.ColorPallet
import com.miraelDev.hikari.ui.theme.Green
import com.miraelDev.hikari.ui.theme.Grey
import com.miraelDev.hikari.ui.theme.Purple700
import com.miraelDev.hikari.ui.theme.Red
import com.miraelDev.hikari.ui.theme.Shapes
import com.miraelDev.hikari.ui.theme.Teal200
import com.miraelDev.hikari.ui.theme.Typography

// dark/light green
private val LightGreenColorPalette = lightColors(
        primary = Green,
        background = Color.White,
        onBackground = Color.Black,
)


private val DarkGreenColorPalette = darkColors(
        primary = Green,
        background = Black,
        onBackground = Color.White,
)

//    dark/light purple

private val LightRedColorPalette = lightColors(
        primary = Red,
        background = Color.White,
        onBackground = Color.Black,
)

private val DarkRedColorPalette = darkColors(
        primary = Red,
        background = Black,
        onBackground = Color.White,
)

//    dark/light blue

private val LightBlueColorPalette = lightColors(
        primary = Color.Yellow,
        primaryVariant = Purple700,
        secondary = Purple700,
        background = Color.White,
        surface = Color.White,
        onBackground = Color.Black,
        onSurface = Color.Black
)

private val DarkBlueColorPalette = darkColors(
        primary = Color.Blue,
        primaryVariant = Color.Blue,
        secondary = Teal200,
        background = Color.Black,
        surface = Color.Black,
        onPrimary = Color.Black,
        onSecondary = Color.White,
        onBackground = Color.White,
        onSurface = Color.White,
        error = Color.Red,
)


//  dark/light orange
private val LightOrangeColorPalette = lightColors(
        primary = Color.Blue,
        primaryVariant = Purple700,
        secondary = Purple700,
        background = Color.White,
        surface = Color.White,
        onBackground = Color.Black,
        onSurface = Color.Black
)

private val DarkOrangeColorPalette = darkColors(
        primary = Purple700,
        primaryVariant = Purple700,
        secondary = Purple700,
        background = Color.Black,
        surface = Color.Black,
        onBackground = Color.White,
        onSurface = Color.White,
        error = Color.Red,
)

@Composable
fun HikariTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        colorPallet: ColorPallet = ColorPallet.GREEN,
        content: @Composable () -> Unit,
) {
    val colors = when (colorPallet) {
        ColorPallet.GREEN -> if (darkTheme) DarkGreenColorPalette else LightGreenColorPalette
        ColorPallet.RED -> if (darkTheme) DarkRedColorPalette else LightRedColorPalette
//        ColorPallet.ORANGE -> if (darkTheme) DarkOrangeColorPalette else LightOrangeColorPalette
//        ColorPallet.BLUE -> if (darkTheme) DarkBlueColorPalette else LightBlueColorPalette
        else -> if (darkTheme) DarkGreenColorPalette else LightGreenColorPalette
    }

    MaterialTheme(
            colors = colors,
            shapes = Shapes,
            typography = Typography,
            content = content
    )
}
