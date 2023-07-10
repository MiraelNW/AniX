package com.miraelDev.anix.presentation.AnimeListScreen.SettingsScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.anix.R

@Composable
fun SettingsScreen(
    onBackPressed: () -> Unit,
    onSettingItemClick: (Int) -> Unit
) {

    BackHandler { onBackPressed() }

    Column(
        modifier = Modifier.systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Toolbar(onBackPressed = onBackPressed)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Filled.Person, contentDescription = "Logo", Modifier.size(128.dp))
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Anix",
                fontSize = 48.sp,
                color = MaterialTheme.colors.primary
            )
        }

        val settingsItems = listOf(
            "Уведомления" to Icons.Filled.Notifications,
            "Язык" to Icons.Filled.Person,
            "Политика сообщества" to Icons.Filled.Settings,
            "Цветовая палитра" to Icons.Filled.Settings
        )

        Column() {
            settingsItems.forEachIndexed { index, textWithIconId ->
                SettingItem(
                    index = index,
                    text = textWithIconId.first,
                    icon = textWithIconId.second,
                    onSettingItemClick = onSettingItemClick
                )
            }
        }

    }
}

@Composable
private fun SettingItem(
    index: Int,
    text: String,
    icon: ImageVector,
    onSettingItemClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onSettingItemClick(index) }
            .padding(8.dp)
            .border(0.dp,MaterialTheme.colors.background)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = icon,
                contentDescription = text,
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = text,
                fontSize = 18.sp
            )
        }

        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = "Show",
            tint = MaterialTheme.colors.primary
        )
    }
}

@Composable
private fun Toolbar(
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .width(8.dp)
                )
                Text(
                    text = "Настройки",
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily.SansSerif,
                )
            }

        }
    }
}