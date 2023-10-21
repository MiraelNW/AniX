package com.miraelDev.vauma.presentation.accountScreen.settings.downloadSettingsScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.miraelDev.vauma.R
import com.miraelDev.vauma.exntensions.noRippleEffectClick
import com.miraelDev.vauma.exntensions.pressClickEffect
import com.miraelDev.vauma.presentation.accountScreen.settings.notificationsScreen.Switcher
import com.miraelDev.vauma.presentation.commonComposFunc.Toolbar


private const val DELETE_VIDEO = 1
private const val CLEAR_CACHE = 2

@Composable
fun DownloadSettings(
    viewModel: DownloadSettingsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {

    val onBackPressedAction = remember { { onBackPressed() } }

    val deleteAllVideosAction = remember { { viewModel.deleteAllVideos() } }
    val changeStatusAction = remember { { viewModel.changeStatus() } }

    val context = LocalContext.current

    val isSelected = remember { { viewModel.isSelected.value } }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var deleteVideosText by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        if (showDeleteDialog) {
            DeleteDialog(
                index = if (deleteVideosText) DELETE_VIDEO else CLEAR_CACHE,
                onDismiss = {
                    showDeleteDialog = false
                },
                onDeleteClick = { index ->

                    showDeleteDialog = false

                    if (index == CLEAR_CACHE) {
                        context.cacheDir.deleteRecursively()
                    } else {
                        deleteAllVideosAction()
                    }

                }
            )
        }

        Column(
            modifier = Modifier.systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Toolbar(onBackPressed = onBackPressedAction, text = R.string.download_video)
            WifiSettingItem(isWifiOnly = isSelected, onWifiClick = changeStatusAction)
            DeleteVideos(
                onDeleteVideosClick = {
                    deleteVideosText = true
                    showDeleteDialog = true
                }
            )
            ClearCache(
                onClearCache = {
                    deleteVideosText = false
                    showDeleteDialog = true
                }
            )
        }
    }

}

@Composable
private fun WifiSettingItem(
    isWifiOnly: () -> Boolean,
    onWifiClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .noRippleEffectClick(onClick = onWifiClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_wifi),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = stringResource(R.string.wi_fi_only),
                fontSize = 18.sp,
                maxLines = 2
            )
        }
        Switcher(isSelectedFun = isWifiOnly)
    }
}

@Composable
private fun DeleteVideos(
    onDeleteVideosClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .pressClickEffect(onClick = onDeleteVideosClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                contentDescription = ""
            )
            Text(text = stringResource(R.string.delete_downloads), fontSize = 18.sp)
        }
    }
}

@Composable
private fun ClearCache(
    onClearCache: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .pressClickEffect(onClick = onClearCache)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_clear),
                contentDescription = ""
            )
            Text(text = stringResource(R.string.clear_cache), fontSize = 18.sp)
        }
    }
}
