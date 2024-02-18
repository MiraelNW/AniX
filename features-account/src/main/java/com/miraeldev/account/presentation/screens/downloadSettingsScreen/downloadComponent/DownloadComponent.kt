package com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent

import kotlinx.coroutines.flow.StateFlow

interface DownloadComponent {

    val model: StateFlow<DownloadStore.State>

    fun onBackPressed()
    fun deleteAllVideos()
    fun changeStatus(isSelected: Boolean)
}