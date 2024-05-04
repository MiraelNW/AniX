package com.miraeldev.account.presentation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.miraeldev.account.presentation.screens.account.accountComponent.AccountComponent
import com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent.DownloadComponent
import com.miraeldev.account.presentation.screens.editProfileScreen.editProfileComponent.EditProfileComponent
import com.miraeldev.account.presentation.screens.notificationsScreen.notificationComponent.NotificationComponent
import com.miraeldev.api.VaumaImageLoader

interface AccountRootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class Account(val component: AccountComponent, val imageLoader: VaumaImageLoader) : Child
        data class EditProfile(val component: EditProfileComponent, val imageLoader: VaumaImageLoader) : Child
        data class Notification(val component: NotificationComponent) : Child
        data class DownloadSettings(val component: DownloadComponent) : Child
//        data class PrivacyPolicy(val component: FilterComponent) : Child
    }
}