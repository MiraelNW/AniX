package com.miraelDev.vauma.presentation.animeInfoDetailAndPlay

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.vauma.R
import com.miraelDev.vauma.domain.models.AnimeDetailInfo


@Composable
fun AnimeNameAndShareButton(
    animeItem: AnimeDetailInfo,
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
//                fontFamily = Montserrat,
                fontSize = 22.sp,
            )
            Text(
                text = animeItem.nameEn,
                overflow = TextOverflow.Ellipsis,
//                fontFamily = Montserrat,
                fontSize = 18.sp,
                color = MaterialTheme.colors.onBackground.copy(0.8f)
            )
        }

        IconButton(onClick = onShareButtonClick) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(R.string.share_button),
                tint = MaterialTheme.colors.primary
            )
        }
    }
}