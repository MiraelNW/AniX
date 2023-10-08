package com.miraelDev.vauma.presentation.accountScreen.settings.privacyPolicy

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miraelDev.vauma.R
import com.miraelDev.vauma.presentation.commonComposFunc.Toolbar

@Composable
fun PrivacyPolicyScreen(onBackPressed: () -> Unit) {
    BackHandler { onBackPressed() }

    Column(modifier = Modifier.systemBarsPadding()) {
        Toolbar(onBackPressed = onBackPressed,text = R.string.privacy_policy)
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


