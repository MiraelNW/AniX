package com.miraelDev.vauma.presentation.commonComposFunc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.miraelDev.vauma.domain.models.anime.AnimeInfo
import com.miraelDev.vauma.exntensions.NoRippleInteractionSource
import com.miraelDev.vauma.exntensions.pressClickEffect
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeCard(animeItem: AnimeInfo, onAnimeItemClick: (Int) -> Unit) {

    Card(
        onClick = { onAnimeItemClick(animeItem.id) },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.background,
        interactionSource = remember { NoRippleInteractionSource() },
        modifier = Modifier.pressClickEffect(),
        elevation = 4.dp
    ) {
        Column{
            Box {
                GlideImage(
                    modifier = Modifier
                        .height(300.dp)
                        .width(200.dp),
                    imageModel = { animeItem.image },
                    imageOptions = ImageOptions(
                        contentDescription = animeItem.nameRu,
                        contentScale = ContentScale.FillBounds

                    ),
                    requestOptions = {
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    },
                )
                Rating(animeItem = animeItem)
            }

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .background(MaterialTheme.colors.background),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 12.dp),
                    text = animeItem.nameRu,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )

            }
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