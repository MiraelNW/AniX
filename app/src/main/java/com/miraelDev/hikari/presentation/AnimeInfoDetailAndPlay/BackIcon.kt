package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BackIcon(onBackPressed: () -> Unit) {
    Box() {
        IconButton(
                modifier = Modifier
                        .statusBarsPadding()
                        .padding(start = 8.dp, top = 8.dp)
                        .size(35.dp)
                        .align(Alignment.TopStart),
                onClick = onBackPressed
        ) {
            Box(
                    modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(0.9f)),
            ) {
                Icon(
                        modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Arrow back",
                        tint = Color.Gray
                )
            }

        }
    }

}