package com.miraeldev.search.presentation.animeCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.exntensions.pressClickEffect
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchAnimeCard(
    item: AnimeInfo,
    onAnimeItemClick: (Int) -> Unit
) {

//    val animatedProgress = remember { Animatable(initialValue = 0f) }
//    LaunchedEffect(Unit) {
//        animatedProgress.animateTo(
//            targetValue = 1f,
//            animationSpec = tween(500)
//        )
//    }

//    val originalGlideUrl = remember {
//        GlideUrl(
//            item.image.original
//        ) {
//            mapOf(
//                Pair(
//                    "Authorization",
//                    item.image.token
//                )
//            )
//        }
//    }

    Card(
        onClick = { onAnimeItemClick(item.id) },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
//            .alpha(animatedProgress.value)
            .pressClickEffect(),
        elevation = 4.dp
    ) {
        Row {

            Box(
                modifier = Modifier
                    .height(220.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                GlideImage(
                    modifier = Modifier.fillMaxWidth(),
                    imageModel = { item.image.original },
                    imageOptions = ImageOptions(
                        contentDescription = item.nameRu,
                        contentScale = ContentScale.FillBounds
                    ),
                    requestOptions = {
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    },
                )
                Rating(animeItem = item)
            }
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .width(16.dp)
            )
            AnimePreview(animeItem = item)


        }
    }
}

@Composable
private fun AnimePreview(animeItem: AnimeInfo) {
    Column(
        modifier = Modifier
            .height(220.dp)
            .padding(top = 8.dp, end = 12.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, end = 16.dp),
                text = animeItem.nameRu,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = animeItem.nameEn,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = animeItem.genres.joinToString(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.4f)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = animeItem.releasedOn.take(4),
                maxLines = 1,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Composable
private fun Rating(animeItem: AnimeInfo) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.primary)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(6.dp),
            text = animeItem.score.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}