package com.miraeldev.account.presentation.screens.account.accountComponent

import com.miraeldev.models.anime.Settings
import kotlinx.coroutines.flow.StateFlow

interface AccountComponent {

    val model: StateFlow<AccountStore.State>

    fun logOut()
    fun onSettingItemClick(settings: Settings)
    fun onDarkThemeClick(isDarkTheme: Boolean)

}