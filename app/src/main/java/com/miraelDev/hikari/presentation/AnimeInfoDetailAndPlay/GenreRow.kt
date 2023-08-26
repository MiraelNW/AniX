package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.hikari.domain.models.AnimeInfo

@Composable
fun GenreRow(animeItem: AnimeInfo) {



    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "Жанры: ", fontSize = 18.sp)

        Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            animeItem.genres.forEachIndexed { index, genre ->
                Text(
                        text = if (index != (animeItem.genres.size-1) ) " ${genre.lowercase()},"
                        else " ${genre.lowercase()}.",
                        fontSize = 18.sp,
                        maxLines = 1
                )
            }
        }

    }
}