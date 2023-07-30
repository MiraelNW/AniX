package com.miraelDev.hikari.presentation.VideoView

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.google.common.collect.ImmutableList

@Stable
data class DropItem(
    val text: String
)

@Composable
fun QualityItems(
    quality: String,
     dropdownItems: ImmutableList<DropItem>,
    modifier: Modifier = Modifier,
    onMenuItemClick: (DropItem) -> Unit,
    onOpenQualityMenu: () -> Unit
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
        Text(
            modifier = Modifier
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
                        onPress = {
                            isContextMenuVisible = true
                            onOpenQualityMenu()
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                    )
                }
                .padding(8.dp),
            text = quality,
            color = Color.White
        )
        DropdownMenu(
            modifier = Modifier.background(Color.Black.copy(alpha = 0.85f)),
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
                onOpenQualityMenu()
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