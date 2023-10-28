package com.miraeldev.detailinfo.presentation

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.detailinfo.R
import com.miraeldev.exntensions.noRippleEffectClick
import com.miraeldev.theme.LocalOrientation
import com.miraeldev.theme.LocalTheme


@Composable
fun RatingAndCategoriesRow(animeItem: AnimeDetailInfo, onRatingClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .noRippleEffectClick(onClick = onRatingClick)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(R.string.rating_star),
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = animeItem.score.toString(),

                color = if (LocalTheme.current) Color.White else MaterialTheme.colors.primary
            )
            Icon(
                modifier = Modifier.padding(start = 4.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_forward),
                contentDescription = stringResource(R.string.rating_star),
                tint = MaterialTheme.colors.primary
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
            if (LocalOrientation.current == Configuration.ORIENTATION_LANDSCAPE)
                Arrangement.spacedBy(36.dp)
            else
                Arrangement.spacedBy(12.dp),
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
                    color = if (LocalTheme.current) Color.White else MaterialTheme.colors.primary
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
                    color = if (LocalTheme.current) Color.White else MaterialTheme.colors.primary
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
                    color = if (LocalTheme.current) Color.White else MaterialTheme.colors.primary
                )
            }
        }

    }
}