package com.miraeldev.account.presentation.screens.notificationsScreen.notificationComponent

import kotlinx.coroutines.flow.StateFlow

interface NotificationComponent {

    val model: StateFlow<NotificationStore.State>

    fun onBackPressed()
}