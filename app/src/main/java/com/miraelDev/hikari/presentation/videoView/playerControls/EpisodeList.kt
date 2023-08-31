package com.miraelDev.hikari.presentation.videoView.playerControls

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.hikari.exntensions.noRippleEffectClick
import com.miraelDev.hikari.ui.theme.DarkGray
import com.miraelDev.hikari.ui.theme.DarkWhite

@Composable
fun EpisodeList(
    modifier: Modifier,
    shouldShowEpisodeList: Boolean,
    orientation: Int,
    onCloseEpisodeList: () -> Unit,
    onEpisodeItemClick: (Int) -> Unit
) {

    val interactionSource = MutableInteractionSource()

    AnimatedVisibility(
        visible = shouldShowEpisodeList,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = modifier
                .background(Color.Black.copy(alpha = 0.3f))
                .noRippleEffectClick(
                    interactionSource = interactionSource,
                    onClick = onCloseEpisodeList
                )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight(if (orientation == Configuration.ORIENTATION_LANDSCAPE) 1f else 0.45f)
                    .fillMaxWidth(if (orientation == Configuration.ORIENTATION_LANDSCAPE) 0.35f else 1f)
                    .navigationBarsPadding()
                    .align(
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) Alignment.CenterEnd else Alignment.BottomCenter
                    )
                    .noRippleEffectClick(MutableInteractionSource()) { },
                elevation = 0.dp,
                shape =
                if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                    RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp)
                else
                    RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                backgroundColor = DarkGray
            ) {
                val scrollState = rememberScrollState()
                val list = listOf(
                    "dfgdgdfgdfgdfgdfg",
                    "dfgdgdfgdfgdfgdfg",
                    "dfgdgdfgdfgdfgdfg",
                    "dfgdgdfgdfgdfgdfg",
                    "dfgdgdfgdfgdfgdfg",
                    "dfgdgdfgdfgdfgdfg",
                    "dfgdgdfgdfgdfgdfg",
                    "dfgdgdfgdfgdfgdfg",
                    "dfgdgdfgdfgdfgdfg",
                    "dfgdgdfgdfgdfgdfg",
                    "dfgdgdfgdfgdfgdfg",
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Row(
                        modifier = Modifier.padding(top = 16.dp, start = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onCloseEpisodeList) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Arrow back",
                                tint = DarkWhite
                            )
                        }
                        Text(text = "Другие эпизоды", fontSize = 24.sp, color = DarkWhite)
                    }
                    Column(
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                start = 20.dp,
                                bottom = 16.dp,
                                end = 20.dp
                            )
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        list.forEachIndexed { index, str ->
                            EpisodeNameItem(
                                index = index,
                                name = str,
                                onEpisodeItemClick = { episodeId ->
                                    onCloseEpisodeList()
                                    onEpisodeItemClick(episodeId)
                                }
                            )
                        }
                    }
                }

            }

        }
    }
}

@Composable
private fun EpisodeNameItem(
    index: Int,
    name: String,
    onEpisodeItemClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEpisodeItemClick(index) }
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Ep${index + 1} $name", fontSize = 20.sp, color = DarkWhite)
            Text(text = "22:01", color = DarkWhite.copy(alpha = 0.8f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(startIndent = 8.dp, color = DarkWhite.copy(alpha = 0.8f), thickness = 1.dp)
    }
}

