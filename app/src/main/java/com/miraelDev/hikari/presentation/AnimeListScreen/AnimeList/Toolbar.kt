package com.miraelDev.hikari.presentation.AnimeListScreen.AnimeList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.hikari.R
import com.miraelDev.hikari.ui.theme.QuickSand

@Composable
fun Toolbar(
        onSettingsClick: () -> Unit,
        onThemeButtonClick: () -> Unit,
        darkTheme: Boolean
) {
    TopAppBar(
            modifier = Modifier.statusBarsPadding(),
            backgroundColor = MaterialTheme.colors.background,
            elevation = 0.dp
    ) {
        Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                        modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
                                .size(32.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_brand_icon),
                        contentDescription = "brand icon",
                        tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colors.primary,
                        fontFamily = QuickSand,
                        fontSize = 24.sp
                )
            }


            SettingsAndThemeButtons(
                    onSettingsClick = onSettingsClick,
                    onThemeButtonClick = onThemeButtonClick,
                    darkTheme = darkTheme
            )

        }
    }
}