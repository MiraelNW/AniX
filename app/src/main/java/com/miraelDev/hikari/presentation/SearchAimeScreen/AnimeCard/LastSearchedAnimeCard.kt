package com.miraelDev.hikari.presentation.SearchAimeScreen.AnimeCard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.miraelDev.hikari.domain.models.AnimeInfo

@Composable
fun LastSearchedAnime(searchName: String,onSearchItemClick: (String) -> Unit) {

    val animatedProgress = remember { Animatable(initialValue = 300f) }
    val opacityProgress = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(300, easing = LinearEasing)
        )
        opacityProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(400)
        )
    }
    val animatedModifier = Modifier
        .graphicsLayer(translationY = animatedProgress.value)
        .alpha(opacityProgress.value)

    Column(
        modifier = animatedModifier
            .clip(shape = RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.background)
            .clickable { onSearchItemClick(searchName) }
            .fillMaxWidth(),
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "last searched animes",
                tint = MaterialTheme.colors.primary
            )
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(16.dp)
            )
            Text(
                text = searchName,
                color = MaterialTheme.colors.onBackground
            )
        }
        Divider(
            startIndent = 8.dp,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.2f),
            thickness = 2.dp
        )
    }
}