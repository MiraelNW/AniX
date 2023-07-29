package com.miraelDev.hikari.presentation.VideoView.playerControls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import com.miraelDev.hikari.R
import com.miraelDev.hikari.entensions.noRippleEffectClick
import com.miraelDev.hikari.ui.theme.LightGreen
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CenterControls(
    modifier: Modifier = Modifier,
    isPlaying: () -> Boolean,
    playbackState: () -> Int,
    onReplayClick: () -> Unit,
    onForwardClick: () -> Unit,
    changeVisibleState: () -> Unit,
) {

    val isVideoPlaying = remember(isPlaying()) { isPlaying() }

    val playerState = remember(playbackState()) { playbackState() }

    var backVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var forwardVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(0.5f)
                    .combinedClickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = changeVisibleState,
                        onDoubleClick = {
                            onReplayClick()
                            backVisible = true
                        },
                        onLongClick = {}
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.Start
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = backVisible,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {

                        Icon(
                            modifier = Modifier.size(36.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_player_back),
                            contentDescription = "player back icon",
                            tint = MaterialTheme.colors.primary
                        )
                        LaunchedEffect(key1 = backVisible) {
                            delay(350)
                            backVisible = false
                        }
                    }

                }
            }

            Box(modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .combinedClickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = changeVisibleState,
                    onDoubleClick = {
                        onForwardClick()
                        forwardVisible = true
                    },
                    onLongClick = {}
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.End
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = forwardVisible,
                        exit = fadeOut()
                    ) {
                        Icon(
                            modifier = Modifier.size(36.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_player_forward),
                            contentDescription = "player forward icon",
                            tint = MaterialTheme.colors.primary
                        )
                        LaunchedEffect(key1 = forwardVisible) {
                            delay(350)
                            forwardVisible = false
                        }
                    }

                }
            }
        }


    }
}


