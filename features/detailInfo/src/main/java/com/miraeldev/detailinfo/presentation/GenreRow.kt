package com.miraeldev.detailinfo.presentation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.detailinfo.R

@Composable
fun GenreRow(animeItem: AnimeDetailInfo) {



    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = stringResource(R.string.genres), fontSize = 18.sp)

        Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            animeItem.genres.forEachIndexed { index, genre ->
                Text(
                        text = if (index != (animeItem.genres.size-1) ) " ${genre.nameRu.lowercase()},"
                        else " ${genre.nameRu.lowercase()}.",
                        fontSize = 18.sp,
                        maxLines = 1
                )
            }
        }

    }
}