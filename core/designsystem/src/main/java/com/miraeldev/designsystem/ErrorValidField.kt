
package com.miraeldev.designsystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun ErrorValidField(modifier: Modifier, isError: Boolean, error: String) {

    AnimatedVisibility(modifier = modifier, visible = isError) {
        Text(text = error, color = Color.Red, fontSize = 14.sp)
    }
}
