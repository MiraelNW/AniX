package com.miraeldev.search.presentation.animeCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.extensions.pressClickEffect
import com.miraeldev.models.anime.AnimeInfo

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchAnimeCard(
    item: AnimeInfo,
    imageLoader: VaumaImageLoader,
    onAnimeItemClick: (Int) -> Unit
) {

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
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = imageLoader.load { data(item.image.original) },
                    contentDescription = item.nameRu,
                    contentScale = ContentScale.FillBounds
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