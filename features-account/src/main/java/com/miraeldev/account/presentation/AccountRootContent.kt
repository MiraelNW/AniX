package com.miraeldev.account.presentation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.miraeldev.account.presentation.screens.account.accountComponent.AccountScreen
import com.miraeldev.account.presentation.screens.downloadSettingsScreen.DownloadSettings
import com.miraeldev.account.presentation.screens.editProfileScreen.EditProfileScreen
import com.miraeldev.account.presentation.screens.notificationsScreen.NotificationScreen

@Composable
fun AccountRootContent(
    accountRootComponent: AccountRootComponent,
    hideBottomBar: () -> Unit,
    showBottomBar: () -> Unit,
) {
    Children(stack = accountRootComponent.stack) {
        when (val instance = it.instance) {
            is AccountRootComponent.Child.Account -> {
                AccountScreen(component = instance.component)
                showBottomBar()
            }
            is AccountRootComponent.Child.EditProfile -> {
                EditProfileScreen(component = instance.component)
                hideBottomBar()
            }
            is AccountRootComponent.Child.Notification -> {
                NotificationScreen(component = instance.component)
                hideBottomBar()
            }
            is AccountRootComponent.Child.DownloadSettings -> {
                DownloadSettings(component = instance.component)
                hideBottomBar()
            }
        }
    }
}