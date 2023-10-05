package com.miraelDev.vauma.exntensions

import android.net.Uri
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
    return merge(this, another)
}

fun Modifier.noRippleEffectClick(
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier {

    return this.clickable(
        interactionSource = MutableInteractionSource(),
        indication = null,
        enabled = enabled,
        onClick = onClick
    )
}

fun Modifier.shimmerEffect(targetValue:Float): Modifier = composed {

    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    scale(scale)
}


fun Modifier.shimmerItem(): Modifier = composed {
    clip(RoundedCornerShape(24.dp))
        .background(MaterialTheme.colors.secondaryVariant)
}

enum class ButtonState { Pressed, Idle }

fun Modifier.pressClickEffect(onClick: (() -> Unit)? = null) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val ty by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0f else -20f)

    this
        .graphicsLayer {
            translationY = ty
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick ?: {}
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}