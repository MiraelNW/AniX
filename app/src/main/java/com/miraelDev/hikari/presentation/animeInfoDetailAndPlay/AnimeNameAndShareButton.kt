package com.miraelDev.hikari.presentation.animeInfoDetailAndPlay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.hikari.domain.models.AnimeInfo


@Composable
fun AnimeNameAndShareButton(
    animeItem: AnimeInfo,
    onShareButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.8f)) {
            Text(
                text = animeItem.nameRu,
                fontSize = 24.sp,
            )
            Text(
                text = animeItem.nameEn,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onBackground.copy(0.8f)
            )
        }

        IconButton(onClick = onShareButtonClick) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "share button",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}