package com.miraeldev.search.presentation.animeCard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.miraeldev.search.R

@Composable
fun LastSearchedAnime(searchName: String, onSearchItemClick: (String) -> Unit) {

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
                imageVector = ImageVector.vectorResource(R.drawable.ic_search),
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