package com.guru.composecookbook.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.miraelDev.hikari.ui.theme.Black
import com.miraelDev.hikari.ui.theme.Blue
import com.miraelDev.hikari.ui.theme.ColorPallet
import com.miraelDev.hikari.ui.theme.Green
import com.miraelDev.hikari.ui.theme.Orange
import com.miraelDev.hikari.ui.theme.Red
import com.miraelDev.hikari.ui.theme.Shapes
import com.miraelDev.hikari.ui.theme.Typography

// dark/light green
private val LightMainColorPalette = lightColors(
        primary = Green,
        background = Color.White,
        onBackground = Color.Black,
)


private val DarkMainColorPalette = darkColors(
        primary = Red,
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
        primary = Blue,
        background = Color.White,
        onBackground = Color.Black,
)

private val DarkBlueColorPalette = darkColors(
        primary = Blue,
        background = Black,
        onBackground = Color.White,
)


//  dark/light orange
private val LightOrangeColorPalette = lightColors(
        primary = Orange,
        background = Color.White,
        onBackground = Color.Black,
)

private val DarkOrangeColorPalette = darkColors(
        primary = Orange,
        background = Black,
        onBackground = Color.White,
)

@Composable
fun HikariTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        colorPallet: ColorPallet = ColorPallet.GREEN,
        content: @Composable () -> Unit,
) {
    val colors = when (colorPallet) {
        ColorPallet.GREEN -> if (darkTheme) DarkMainColorPalette else LightMainColorPalette
        ColorPallet.ORANGE -> if (darkTheme) DarkOrangeColorPalette else LightOrangeColorPalette
        ColorPallet.PURPLE -> if (darkTheme) DarkBlueColorPalette else LightBlueColorPalette
//        ColorPallet.BLUE -> if (darkTheme) DarkBlueColorPalette else LightBlueColorPalette
        else -> if (darkTheme) DarkMainColorPalette else LightMainColorPalette
    }

    MaterialTheme(
            colors = colors,
            shapes = Shapes,
            typography = Typography,
            content = content
    )
}
