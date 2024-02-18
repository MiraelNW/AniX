package com.miraeldev.account.presentation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.miraeldev.account.presentation.screens.account.accountComponent.AccountComponent
import com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent.DownloadComponent
import com.miraeldev.account.presentation.screens.editProfileScreen.EditProfileComponent.EditProfileComponent
import com.miraeldev.account.presentation.screens.notificationsScreen.notificationComponent.NotificationComponent

interface AccountRootComponent {


    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class Account(val component: AccountComponent) : Child
        data class EditProfile(val component: EditProfileComponent) : Child
        data class Notification(val component: NotificationComponent) : Child
        data class DownloadSettings(val component: DownloadComponent) : Child
//        data class PrivacyPolicy(val component: FilterComponent) : Child

    }
}