package com.miraeldev.detailinfo.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.miraeldev.anime.AnimeDetailInfo
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TopAnimeImage(
    animeItem: AnimeDetailInfo,
) {
    Card(
        elevation = 4.dp
    ) {
        Box {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                imageModel = { animeItem.image },
                requestOptions = {
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                },
                imageOptions = ImageOptions(
                    contentDescription = "anime image preview",
                    contentScale = ContentScale.FillBounds,
                ),
                component = rememberImageComponent {
//                    +BlurTransformationPlugin(radius = 30)
                }
            )
            Card(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .size(150.dp, 250.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(16.dp),
                elevation = 4.dp
            ) {
                GlideImage(
                    modifier = Modifier.fillMaxSize(),
                    imageModel = { animeItem.image },
                    imageOptions = ImageOptions(
                        contentDescription = "anime image preview",
                    ),
                    requestOptions = {
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    },
                )
            }

        }
    }
}