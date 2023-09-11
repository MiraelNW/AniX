package com.miraelDev.vauma.presentation.animeInfoDetailAndPlay

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.miraelDev.vauma.domain.models.AnimeDetailInfo
import com.miraelDev.vauma.presentation.mainScreen.LocalOrientation
import com.miraelDev.vauma.presentation.mainScreen.LocalTheme
import com.miraelDev.vauma.ui.theme.LightGreen700


@Composable
fun RatingAndCategoriesRow(animeItem: AnimeDetailInfo) {
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
            Text(
                text = animeItem.score.toString(),
                color = if (LocalTheme.current) LightGreen700 else MaterialTheme.colors.primary
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement =
            if (LocalOrientation.current == Configuration.ORIENTATION_LANDSCAPE)
                Arrangement.spacedBy(36.dp)
            else
                Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(16.dp)
                    ),
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = animeItem.kind,
                    fontSize = 16.sp,
                    color = if (LocalTheme.current) LightGreen700 else MaterialTheme.colors.primary
                )
            }

            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(16.dp)
                    ),
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "${animeItem.episodes} episodes",
                    fontSize = 16.sp,
                    color = if (LocalTheme.current) LightGreen700 else MaterialTheme.colors.primary
                )
            }

            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(16.dp)
                    ),
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = animeItem.status,
                    fontSize = 16.sp,
                    color = if (LocalTheme.current) LightGreen700 else MaterialTheme.colors.primary
                )
            }
        }

    }
}