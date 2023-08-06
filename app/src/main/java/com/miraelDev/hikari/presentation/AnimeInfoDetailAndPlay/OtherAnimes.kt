package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.exntensions.pressClickEffect

@Composable
fun OtherAnimes(
    animeList: List<AnimeInfo>,
    onAnimeItemClick: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(8.dp),
        contentPadding = PaddingValues(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = animeList, key = { it.id }) {
            AnimeCard(animeItem = it, onAnimeItemClick = onAnimeItemClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AnimeCard(animeItem: AnimeInfo, onAnimeItemClick: (Int) -> Unit) {

    val animatedProgress = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(700)
        )
    }

    val animatedModifier = Modifier.alpha(animatedProgress.value)
    Column(
        modifier = animatedModifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            onClick = { onAnimeItemClick(animeItem.id) },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier
                .size(width = 175.dp, height = 275.dp)
                .fillMaxWidth()
                .pressClickEffect(),
            elevation = 4.dp
        ) {

            AsyncImage(
                modifier = Modifier
                    .height(300.dp)
                    .width(230.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = animeItem.image,
                contentDescription = animeItem.nameEn,
                contentScale = ContentScale.FillBounds,

                )

        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            text = animeItem.nameRu,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}