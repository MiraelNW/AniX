package com.miraeldev.account.presentation.screens.account.accountComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.OnLogOut
import com.miraeldev.models.OnSettingClick
import com.miraeldev.models.anime.Settings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultAccountComponentFactory = (ComponentContext, OnSettingClick, OnLogOut) -> DefaultAccountComponent

@Inject
class DefaultAccountComponent(
    private val accountStoreFactory: AccountStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onSettingItemClick: (Settings) -> Unit,
    @Assisted onLogOutComplete: () -> Unit
) : AccountComponent, ComponentContext by componentContext {

    private val store: AccountStore = instanceKeeper.getStore { accountStoreFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is AccountStore.Label.OnSettingItemClick -> onSettingItemClick(it.settings)
                    is AccountStore.Label.OnLogOutComplete -> onLogOutComplete()
                }
            }
        }
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<AccountStore.State> = store.stateFlow

    override fun logOut() {
        store.accept(AccountStore.Intent.LogOut)
    }

    override fun onSettingItemClick(settings: Settings) {
        store.accept(AccountStore.Intent.OnSettingItemClick(settings))
    }

    override fun onDarkThemeClick(isDarkTheme: Boolean) {
        store.accept(AccountStore.Intent.OnDarkThemeClick(isDarkTheme))
    }
}