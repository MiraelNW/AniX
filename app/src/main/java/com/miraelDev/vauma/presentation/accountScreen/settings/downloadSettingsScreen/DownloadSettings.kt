package com.miraelDev.vauma.presentation.accountScreen.settings.downloadSettingsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.miraelDev.vauma.R
import com.miraelDev.vauma.presentation.commonComposFunc.Toolbar

@Composable
fun DownloadSettings(){
    Column {
        Toolbar(onBackPressed = { /*TODO*/ }, text = R.string.download_video)
    }
}

