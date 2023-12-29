package com.miraeldev.exntensions

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
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

fun Modifier.shimmerEffect(targetValue:Float,initialValue:Float = 0.98f): Modifier = composed {

    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = initialValue,
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
    var buttonState by remember { mutableStateOf(com.miraeldev.exntensions.ButtonState.Idle) }
    val ty by animateFloatAsState(if (buttonState == com.miraeldev.exntensions.ButtonState.Pressed) 0f else -20f)

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
                buttonState = if (buttonState == com.miraeldev.exntensions.ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    com.miraeldev.exntensions.ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    com.miraeldev.exntensions.ButtonState.Pressed
                }
            }
        }
}

inline fun <reified Activity : ComponentActivity> Context.getActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}