package com.miraelDev.vauma.presentation.commonComposFunc

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.vauma.presentation.mainScreen.LocalOrientation

@Composable
fun ErrorRefreshItem(
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    val orientationLandscape = LocalOrientation.current == Configuration.ORIENTATION_LANDSCAPE

    OutlinedButton(
        onClick = onClickRetry,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.primary.copy(0.3f)
        )
    ) {
        Text(text = "Обновить", fontSize = if (orientationLandscape) 18.sp else 24.sp)
    }
}

@Composable
fun ErrorAppendItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            color = Color.Red
        )
        OutlinedButton(
            onClick = onClickRetry,
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colors.primary.copy(0.3f)
            )
        ) {
            Text(text = "Обновить", fontSize = 20.sp)
        }
    }
}

@Composable
fun ErrorAppendMessage(
    message: String,
) {
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = message,
        maxLines = 1,
        style = MaterialTheme.typography.h6,
        color = Color.Red
    )
}

@Composable
fun ErrorRetryButton(onClickRetry: () -> Unit) {
    OutlinedButton(
        onClick = onClickRetry,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.primary.copy(0.3f)
        )
    ) {
        Text(text = "Обновить", fontSize = 16.sp)
    }
}
