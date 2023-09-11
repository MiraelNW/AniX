package com.miraelDev.vauma.presentation.shimmerList

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miraelDev.vauma.exntensions.shimmerEffect

@Composable
@Preview(showBackground = true)
fun ShimmerList() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp, end = 4.dp)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(6) {
            ShimmerAnimeCard()
        }
    }

}

@Composable
fun ShimmerItem() {
    ShimmerAnimeCard()
}

@Composable
private fun ShimmerAnimeCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        elevation = 4.dp
    ) {
        Row() {
            Spacer(
                modifier = Modifier
                    .height(220.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shimmerEffect()
            )
            Spacer(
                modifier = Modifier
                    .height(150.dp)
                    .width(16.dp)
            )
            AnimePreview()

        }
    }
}

@Composable
private fun AnimePreview() {
    Column(
        Modifier
            .padding(top = 4.dp, end = 8.dp)
            .height(220.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .shimmerEffect()
                )
                Rating()
            }

            Spacer(
                modifier = Modifier
                    .height(6.dp)
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shimmerEffect()
            )
        }

        repeat(3) {
            Spacer(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shimmerEffect()
            )
        }
    }
}

@Composable
private fun Rating() {
    Row {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .clip(RoundedCornerShape(16.dp))
                .shimmerEffect()
                .height(16.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .clip(RoundedCornerShape(16.dp))
                .shimmerEffect()
                .height(16.dp)
        )
    }
}