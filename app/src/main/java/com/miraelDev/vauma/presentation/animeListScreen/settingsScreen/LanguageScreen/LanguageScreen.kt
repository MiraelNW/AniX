package com.miraelDev.vauma.presentation.animeListScreen.settingsScreen.LanguageScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.vauma.R

@Composable
fun LanguageScreen(onBackPressed: () -> Unit) {
    BackHandler { onBackPressed() }
    Column(modifier = Modifier.systemBarsPadding()) {
        Toolbar(onBackPressed = onBackPressed)
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
                    text = "Выбор языка",
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily.SansSerif,
                )
            }

        }
    }
}