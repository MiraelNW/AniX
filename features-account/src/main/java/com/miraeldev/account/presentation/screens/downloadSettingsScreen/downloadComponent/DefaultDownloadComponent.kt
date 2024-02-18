package com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent

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

class DefaultDownloadComponent @AssistedInject constructor(
    private val storeFactory: DownloadStoreFactory,
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("onBackClicked") onBackClicked: () -> Unit
) : DownloadComponent, ComponentContext by componentContext {

    private val store: DownloadStore = instanceKeeper.getStore { storeFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    DownloadStore.Label.OnBackPressed -> onBackClicked()
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<DownloadStore.State> = store.stateFlow

    override fun onBackPressed() {
        store.accept(DownloadStore.Intent.OnBackPressed)
    }

    override fun deleteAllVideos() {
        store.accept(DownloadStore.Intent.DeleteAllVideos)
    }

    override fun changeStatus(isSelected: Boolean) {
        store.accept(DownloadStore.Intent.ChangeStatus(isSelected))
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onBackClicked") onBackClicked: () -> Unit
        ): DefaultDownloadComponent
    }
}