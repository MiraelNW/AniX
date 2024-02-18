package com.miraeldev.search.presentation.filterScreen.filterComponent

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

class DefaultFilterComponent @AssistedInject constructor(
    private val storeFactory: FilterStoreFactory,
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("onBackPressed") onBackPressed: () -> Unit,
    ) : FilterComponent, ComponentContext by componentContext {

    private val store: FilterStore = instanceKeeper.getStore { storeFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is FilterStore.Label.OnBackPressed -> onBackPressed()
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<FilterStore.State> = store.stateFlow

    override fun selectCategory(categoryId: Int, category: String, isSelected: Boolean) {
        store.accept(FilterStore.Intent.SelectCategory(categoryId, category, isSelected))
    }

    override fun clearAllFilter() {
        store.accept(FilterStore.Intent.ClearAllFilter)
    }

    override fun onBackPressed() {
        store.accept(FilterStore.Intent.OnBackPressed)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onBackPressed") onBackPressed: () -> Unit,
        ): DefaultFilterComponent
    }
}