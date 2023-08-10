package com.miraelDev.hikari.presentation.SearchAimeScreen.AnimeCard

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import coil.util.DebugLogger
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.exntensions.pressClickEffect
import com.miraelDev.hikari.ui.theme.Gold

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeCard(
        item: AnimeInfo,
        onAnimeItemClick: (Int) -> Unit
) {

    val animatedProgress = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(700)
        )
    }
    val animatedModifier = Modifier.alpha(animatedProgress.value)

    Card(
            onClick = { onAnimeItemClick(item.id) },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier
                    .fillMaxWidth()
                    .pressClickEffect(),
            elevation = 4.dp
    ) {
        Row(modifier = animatedModifier) {

            AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                            .data(item.image)
                            .crossfade(true)
                            .build(),
                    contentDescription = item.nameEn,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                            .height(150.dp)
                            .width(100.dp)
                            .clip(RoundedCornerShape(16.dp))
            )
            Spacer(
                    modifier = Modifier
                            .height(150.dp)
                            .width(16.dp)
            )
            AnimePreview(animeItem = item)


        }
    }
}

@Composable
private fun AnimePreview(animeItem: AnimeInfo) {
    Column(
            Modifier.padding(top = 4.dp, end = 8.dp)
    ) {
        Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = animeItem.nameEn, color = MaterialTheme.colors.onBackground)
            Rating(animeItem = animeItem)
        }
        Text(text = animeItem.nameRu, color = MaterialTheme.colors.onBackground.copy(alpha = 0.4f))
        Spacer(
                modifier = Modifier
                        .height(4.dp)
                        .fillMaxWidth()
        )
        Text(
                text = animeItem.description,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
private fun Rating(animeItem: AnimeInfo) {
    Row {
        Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Rating",
                tint = Gold
        )
        Text(text = animeItem.score.toString(), color = MaterialTheme.colors.onBackground)
    }
}