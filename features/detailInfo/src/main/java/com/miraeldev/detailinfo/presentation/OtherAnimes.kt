package com.miraeldev.detailinfo.presentation

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.miraeldev.anime.Similar
import com.miraeldev.exntensions.pressClickEffect
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.collections.immutable.ImmutableList

@Composable
fun OtherAnime(
    animeList: ImmutableList<Similar>,
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
private fun AnimeCard(animeItem: Similar, onAnimeItemClick: (Int) -> Unit) {

//    val originalGlideUrl = remember {
//        GlideUrl(
//            animeItem.image.original
//        ) {
//            mapOf(
//                Pair(
//                    "Authorization",
//                    animeItem.image.token
//                )
//            )
//        }
//    }

    val animatedProgress = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(700)
        )
    }

    val animatedModifier = Modifier.alpha(animatedProgress.value)
    Column(
        modifier = animatedModifier.width(230.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            onClick = { onAnimeItemClick(animeItem.id) },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier
                .size(width = 230.dp, height = 275.dp)
                .fillMaxWidth()
                .pressClickEffect(),
            elevation = 4.dp
        ) {

            GlideImage(
                modifier = Modifier
                    .height(300.dp)
                    .width(230.dp)
                    .clip(RoundedCornerShape(16.dp)),
                imageModel = { animeItem.image.original },
                imageOptions = ImageOptions(
                    contentDescription = "anime image preview",
                    contentScale = ContentScale.FillBounds,
                ),
                requestOptions = {
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                },
            )

        }
        Text(
            modifier = Modifier
                .width(230.dp)
                .padding(4.dp),
            text = animeItem.nameRu,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontSize = 18.sp
        )
    }
}