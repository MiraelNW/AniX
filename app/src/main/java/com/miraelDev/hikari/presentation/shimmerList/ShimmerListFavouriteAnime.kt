package com.miraelDev.hikari.presentation.shimmerList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miraelDev.hikari.exntensions.shimmerEffect

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview(showBackground = true)
fun ShimmerListFavouriteAnime() {

    val scrollState = rememberScrollState()

    FlowRow(
            modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 4.dp, end = 4.dp)
                    .navigationBarsPadding()
                    .verticalScroll(scrollState),
            horizontalArrangement = Arrangement.SpaceEvenly,
            maxItemsInEachRow = 2
    ) {
        repeat(6) {
            ShimmerAnimeCard()
        }
    }

}

@Composable
private fun ShimmerAnimeCard() {
    Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier
                    .height(300.dp)
                    .width(170.dp)
                    .padding(vertical = 8.dp)
            ,
            elevation = 4.dp
    ) {
        Row() {
            Spacer(
                    modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .shimmerEffect()
            )
        }
    }
}

