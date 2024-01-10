package com.miraeldev.video.presentation.playerControls

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.miraeldev.videoscreen.R
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CenterControls(
    modifier: Modifier,
    onReplayClick: () -> Unit,
    onForwardClick: () -> Unit,
    changeVisibleState: () -> Unit,
) {

    var backVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var forwardVisible by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = backVisible) {
        delay(350)
        backVisible = false
    }

    LaunchedEffect(key1 = forwardVisible) {
        delay(350)
        forwardVisible = false
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
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
                }

            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
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
                }

            }
        }
    }
}



