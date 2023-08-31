package com.miraelDev.hikari.presentation.animeInfoDetailAndPlay

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.hikari.domain.models.AnimeInfo


@Composable
fun RatingAndCategoriesRow(animeItem: AnimeInfo) {
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
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "rating star",
                tint = MaterialTheme.colors.primary
            )
            Text(text = animeItem.score.toString(), color = MaterialTheme.colors.primary)
        }

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colors.primary)
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = animeItem.kind,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary
                )
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colors.primary)
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = "${animeItem.episodes} episodes",
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary
                )
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colors.primary)
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = animeItem.status,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary
                )
            }
        }

    }
}