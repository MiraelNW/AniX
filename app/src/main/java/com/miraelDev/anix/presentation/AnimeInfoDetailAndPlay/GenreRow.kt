package com.miraelDev.anix.presentation.AnimeInfoDetailAndPlay

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.anix.domain.models.AnimeInfo

@Composable
fun GenreRow(animeItem: AnimeInfo) {
    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "Жанры: ", fontSize = 18.sp)
        animeItem.genres.forEachIndexed { index, genre ->
            Text(
                text = if (index != animeItem.genres.size) " ${genre.lowercase()},"
                else " ${genre.lowercase()}",
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}