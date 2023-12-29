package com.miraeldev.presentation.shimmerList

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miraeldev.exntensions.shimmerEffect
import com.miraeldev.exntensions.shimmerItem

@Composable
@Preview(showBackground = true)
fun ShimmerListAnimeDetail() {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .shimmerEffect(initialValue = 1.02f, targetValue = 1.05f)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
                .shimmerItem()
        )
    }
}


@Composable
private fun ShimmerAnimeNameAndShareButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .shimmerEffect(1f)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Column(modifier = Modifier.fillMaxWidth(0.8f)) {
            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
                    .shimmerItem()
            )
            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.8f)
                    .shimmerItem()
            )

        }

        Spacer(
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
                .shimmerItem()
        )
    }
}

@Composable
private fun ShimmerPlayButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shimmerEffect(1f)
            .height(72.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                .shimmerItem()
        )
    }


}

@Composable
private fun ShimmerRatingAndCategoriesRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .shimmerEffect(1f)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .width(48.dp)
                    .height(24.dp)
                    .shimmerItem()
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(24.dp)
                    .shimmerItem()
            )
        }

    }
}

@Composable
private fun ShimmerGenreRow() {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .shimmerEffect(1f)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(24.dp)
                .shimmerItem()
        )
    }
}

@Composable
private fun ShimmerExpandableDescription() {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .shimmerEffect(1f)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .shimmerItem()
        )
    }
}

@Composable
private fun ShimmerOtherAnimeText() {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .shimmerEffect(1f)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(24.dp)
                .shimmerItem()
        )
    }
}

@Composable
private fun ShimmerOtherAnimes() {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .shimmerEffect(1f)
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
        modifier = Modifier
            .fillMaxWidth()
            .shimmerEffect(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Spacer(
            modifier = Modifier
                .height(250.dp)
                .width(200.dp)
                .shimmerItem()
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .padding(4.dp)
                .shimmerItem()
        )
    }
}