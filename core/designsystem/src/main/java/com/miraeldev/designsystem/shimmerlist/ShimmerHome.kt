
package com.miraeldev.designsystem.shimmerlist

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
import com.miraeldev.extensions.shimmerEffect
import com.miraeldev.extensions.shimmerItem

@Composable
@Preview(showBackground = true)
fun ShimmerHome() {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShimmerAnimeImage()

        ShimmerCategoryAnime()
        ShimmerCategoryAnime()
        ShimmerCategoryAnime()
        ShimmerCategoryAnime()
    }

}

@Composable
private fun ShimmerAnimeImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .shimmerEffect(initialValue = 1.02f, targetValue = 1.05f)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
                .shimmerItem()
        )
    }
}

@Composable
private fun ShimmerCategoryAnime() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shimmerEffect(1f)
                .padding(horizontal = 14.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .width(72.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .shimmerItem()
            )
            Spacer(
                modifier = Modifier
                    .width(48.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .shimmerItem()
            )
        }

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
                .width(175.dp)
                .shimmerItem()
        )
    }
}
