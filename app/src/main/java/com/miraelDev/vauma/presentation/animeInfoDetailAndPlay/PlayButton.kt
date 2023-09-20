package com.miraelDev.vauma.presentation.animeInfoDetailAndPlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.vauma.R
import com.miraelDev.vauma.exntensions.NoRippleInteractionSource

@Composable
fun PlayButton(
    onPlayClick: () -> Unit,
    onDownloadClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f),
            onClick = onPlayClick,
            interactionSource = NoRippleInteractionSource(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
            ) {
                Icon(
                    modifier = Modifier.padding(2.dp),
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = stringResource(R.string.play_icon),
                    tint = MaterialTheme.colors.primary
                )
            }

            Text(
                modifier = Modifier.padding(4.dp),
                text = stringResource(R.string.play),
                color = Color.White,
                fontSize = 18.sp
            )
        }

        IconButton(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.8f)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.primary),
            interactionSource = NoRippleInteractionSource(),
            onClick = onDownloadClick
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_download),
                contentDescription = stringResource(id = R.string.download),
                tint = Color.White
            )
        }
    }


}