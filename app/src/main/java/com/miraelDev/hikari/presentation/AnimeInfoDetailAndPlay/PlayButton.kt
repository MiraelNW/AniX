package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.hikari.exntensions.pressClickEffect

@Composable
fun PlayButton(onPlayClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .pressClickEffect()
            .padding(top = 30.dp, start = 16.dp, end = 16.dp),
        onClick = onPlayClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
        ) {
            Icon(
                modifier = Modifier.padding(2.dp),
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "play icon",
                tint = MaterialTheme.colors.primary
            )
        }

        Text(
            modifier = Modifier.padding(4.dp),
            text = "Play",
            color = Color.White,
            fontSize = 18.sp
        )
    }
}