package com.miraelDev.hikari.presentation.VideoView

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.miraelDev.hikari.R

data class EpisodeDropItem(
    val text: String,
//    val episodeRef:String
)

@Composable
fun EpisodesItems(
    dropdownItems: List<EpisodeDropItem>,
    modifier: Modifier = Modifier,
    onMenuItemClick: (EpisodeDropItem) -> Unit,
    onEpisodeIconClick: () -> Unit,
) {

    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    var itemWidth by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .background(Color.Transparent)
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
                itemWidth = with(density) { it.width.toDp() }
            }
    ) {
        IconButton(
            modifier = Modifier
                ,
            onClick = {}
        ) {
            Icon(
                modifier = Modifier.size(44.dp)
                    .indication(interactionSource, LocalIndication.current)
                    .pointerInput(true) {
                        detectTapGestures(
                            onPress = {
                                onEpisodeIconClick()
                                isContextMenuVisible = true
                                pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                            },
                        )
                    }
                    .padding(8.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_episodes),
                contentDescription = "episodes",
                tint = MaterialTheme.colors.primary
            )
        }
        DropdownMenu(
            modifier = Modifier.background(Color.Black.copy(alpha = 0.85f)),
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            offset = pressOffset.copy(
                y = (pressOffset.y - itemHeight),
                x = pressOffset.x - itemWidth
            )
        ) {
            dropdownItems.forEach {
                DropdownMenuItem(onClick = {
                    onMenuItemClick(it)
                    isContextMenuVisible = false
                }) {
                    Text(text = it.text, color = Color.White)
                }
            }
        }
    }
}
