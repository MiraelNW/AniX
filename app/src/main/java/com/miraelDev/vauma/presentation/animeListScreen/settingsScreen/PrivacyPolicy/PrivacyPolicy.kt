package com.miraelDev.vauma.presentation.animeListScreen.settingsScreen.PrivacyPolicy

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
fun PrivacyPolicyScreen(onBackPressed: () -> Unit) {
    BackHandler { onBackPressed() }

    Column(modifier = Modifier.systemBarsPadding()) {
        Toolbar(onBackPressed = onBackPressed)
        PrivacyPolicyText()
    }
}

@Composable
private fun PrivacyPolicyText(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {

    }
    Text(
        modifier = Modifier.padding(8.dp),
        text = "lfkgsl;kg;gksl;gks;gks;gksl;gks;fks;lfks;lfks;lfksl;fksdl;fks;fksd;lfks;lfk;sfks;"
    )
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
                    text = "Политика сообщества",
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily.SansSerif,
                )
            }

        }
    }
}

