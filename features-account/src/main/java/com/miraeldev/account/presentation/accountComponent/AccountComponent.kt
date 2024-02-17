package com.miraeldev.account.presentation.accountComponent

import kotlinx.coroutines.flow.StateFlow

interface AccountComponent {

    val model: StateFlow<AccountStore.State>

    fun logOut()
    fun onSettingItemClick(id: Int)
    fun onDarkThemeClick()

}