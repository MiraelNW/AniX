package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FavouriteIcon(
        modifier: Modifier,
        size: Int = 42,
        selected: Boolean = false,
        onFavouriteIconClick: (Boolean) -> Unit
) {
    var isSelected by remember { mutableStateOf(selected) }

    var scaleForIconFloat by remember { mutableStateOf(0f) }

    val scaleForIcon by animateFloatAsState(
            targetValue = scaleForIconFloat,
            tween(
                    durationMillis = 450,
                    easing = LinearEasing
            ),
            label = ""
    )

    var scaleForIconBorderFloat by remember { mutableStateOf(1f) }

    val scaleForIconBorder by animateFloatAsState(
            targetValue = scaleForIconBorderFloat,
            tween(
                    delayMillis = 250,
                    durationMillis = 400,
                    easing = LinearEasing
            ),
            label = ""
    )

    LaunchedEffect(key1 = isSelected) {
        if (isSelected) {
            scaleForIconFloat = 1f
            scaleForIconBorderFloat = 0f
        } else {
            scaleForIconFloat = 0f
            scaleForIconBorderFloat = 1f
        }
    }

    IconButton(
            modifier = modifier.size(size.dp),
            onClick = {
                isSelected = !isSelected
                onFavouriteIconClick(isSelected)
            }
    ) {

        Icon(
                modifier = Modifier
                        .fillMaxSize()
                        .scale(scaleForIconBorder),
                tint = Color.White,
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = "favourite icon"
        )

        Icon(
                modifier = Modifier
                        .fillMaxSize()
                        .scale(scaleForIcon),
                tint = Color.Red,
                imageVector = Icons.Filled.Favorite,
                contentDescription = "favourite icon"
        )


    }
}