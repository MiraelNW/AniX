package com.miraelDev.hikari.presentation.ShimmerList

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.exntensions.pressClickEffect
import com.miraelDev.hikari.exntensions.shimmerEffect
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.transformation.blur.BlurTransformationPlugin

@Composable
@Preview(showBackground = true)
fun ShimmerListAnimeDetail() {

    val scrollState = rememberScrollState()

    Column(
            modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ShimmerAnimeImage()

        ShimmerAnimeNameAndShareButton()

        ShimmerPlayButton()

        ShimmerRatingAndCategoriesRow()

        ShimmerGenreRow()

        ShimmerExpandableDescription()

        ShimmerOtherAnimeText()

        ShimmerOtherAnimes()
    }

}

@Composable
private fun ShimmerAnimeImage() {
    Box {
        Spacer(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .shimmerEffect(),
        )
    }
}

@Composable
private fun ShimmerAnimeNameAndShareButton() {
    Row(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.8f)) {
            Spacer(modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
                    .shimmerEffect())
            Spacer(modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.8f)
                    .shimmerEffect())

        }

        Spacer(modifier = Modifier
                .height(24.dp)
                .width(24.dp)
                .shimmerEffect()
        )
    }
}

@Composable
private fun ShimmerPlayButton() {
    Spacer(
            modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                    .shimmerEffect()
    )
}

@Composable
private fun ShimmerRatingAndCategoriesRow() {
    Row(
            modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
                modifier = Modifier.padding(2.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier
                    .width(48.dp)
                    .height(24.dp)
                    .shimmerEffect()
            )

        }

        Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(24.dp)
                    .shimmerEffect()
            )
        }

    }
}

@Composable
private fun ShimmerGenreRow() {
    Row(
            modifier = Modifier.padding(8.dp)
    ) {
        Spacer(modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(24.dp)
                .shimmerEffect())
    }
}

@Composable
private fun ShimmerExpandableDescription() {
    Row(
            modifier = Modifier.padding(8.dp)
    ) {
        Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(156.dp)
                .shimmerEffect())
    }
}

@Composable
private fun ShimmerOtherAnimeText() {
    Row(
            modifier = Modifier.padding(8.dp)
    ) {
        Spacer(modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(24.dp)
                .shimmerEffect())
    }
}

@Composable
private fun ShimmerOtherAnimes() {
    Row(
            modifier = Modifier
                    .padding(8.dp)
                    .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(3) {
            AnimeCard()
        }
    }
}


@Composable
private fun AnimeCard() {

    Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Spacer(
                modifier = Modifier
                        .height(250.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .shimmerEffect(),
        )

        Spacer(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .padding(4.dp)
                        .shimmerEffect()
        )
    }
}