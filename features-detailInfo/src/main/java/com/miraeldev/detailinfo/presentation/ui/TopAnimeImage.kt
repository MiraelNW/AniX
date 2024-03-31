package com.miraeldev.detailinfo.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.transformations
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.api.transformation.BlurTransformation

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopAnimeImage(
    imageLoader: VaumaImageLoader,
    animeItem: AnimeDetailInfo,
    onImageClick: () -> Unit
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
        onClick = onImageClick
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                model = imageLoader.load {
                    data(animeItem.image.preview)
                    transformations(BlurTransformation(radius = 5, scale = 1f))
                },
                contentDescription = "anime image preview",
                contentScale = ContentScale.FillBounds,
            )
            Card(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .size(150.dp, 250.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(16.dp),
                elevation = 4.dp
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = imageLoader.load { data(animeItem.image.original) },
                    contentDescription = "anime image preview"
                )
            }

        }
    }
}