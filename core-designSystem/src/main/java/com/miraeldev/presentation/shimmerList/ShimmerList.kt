package com.miraeldev.presentation.shimmerList

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.miraeldev.extensions.shimmerEffect
import com.miraeldev.extensions.shimmerItem
import com.miraeldev.theme.LocalOrientation

@Composable
fun ShimmerGrid(targetValue: Float) {

    val orientation = LocalOrientation.current
    val cells = remember {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            3
        } else {
            2
        }
    }

    LazyVerticalGrid(
        modifier = Modifier
            .navigationBarsPadding(),
        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = 12.dp,
            start = 4.dp,
            end = 4.dp
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        columns = GridCells.Fixed(cells)
    ) {
        items(count = 6){
            ShimmerAnimeCard(targetValue = targetValue, Modifier.fillMaxWidth(0.5f))
        }
    }
}

@Composable
fun ShimmerItem() {
    ShimmerAnimeCard(targetValue = 1.5f, modifier = Modifier)
}

@Composable
fun ShimmerAnimeCard(targetValue: Float, modifier: Modifier) {
    Card(
        modifier = modifier.shimmerEffect(targetValue),
        shape = RoundedCornerShape(24.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp
    ) {
        Column {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .shimmerItem()
            )
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
                        .shimmerItem()
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
                    .shimmerItem()
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
                    .shimmerItem()
            )
        }
    }
}

@Composable
private fun Rating() {
    Row(
        modifier = Modifier
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .height(16.dp)
                .shimmerItem()
        )
        Spacer(modifier = Modifier.width(2.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(16.dp)
                .shimmerItem()
        )
    }
}