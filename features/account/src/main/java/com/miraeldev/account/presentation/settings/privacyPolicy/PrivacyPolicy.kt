package com.miraeldev.account.presentation.settings.privacyPolicy

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.miraeldev.account.R
import com.miraeldev.presentation.Toolbar

@Composable
fun PrivacyPolicyScreen(onBackPressed: () -> Unit) {
    BackHandler { onBackPressed() }

    Column(modifier = Modifier.systemBarsPadding()) {
        Toolbar(
            onBackPressed = onBackPressed,
            text = R.string.privacy_policy
        )
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


