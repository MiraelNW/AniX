package com.miraeldev.account.presentation.screens.notificationsScreen.notificationComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultNotificationComponent @AssistedInject constructor(
    private val storeFactory: NotificationStoreFactory,
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
) : NotificationComponent, ComponentContext by componentContext {

    private val store: NotificationStore = instanceKeeper.getStore { storeFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is NotificationStore.Label.OnBackClicked -> onBackClicked()
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<NotificationStore.State> = store.stateFlow

    override fun onBackPressed() {
        store.accept(NotificationStore.Intent.OnBackClicked)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
        ): DefaultNotificationComponent
    }
}