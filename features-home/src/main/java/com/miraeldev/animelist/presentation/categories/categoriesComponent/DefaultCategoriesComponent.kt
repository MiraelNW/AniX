package com.miraeldev.animelist.presentation.categories.categoriesComponent

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

class DefaultCategoriesComponent @AssistedInject constructor(
    private val storeFactory: CategoriesStoreFactory,
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("onAnimeItemClick") onAnimeItemClick: (Int) -> Unit,
) : CategoriesComponent, ComponentContext by componentContext {

    private val store: CategoriesStore = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<CategoriesStore.State> = store.stateFlow

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is CategoriesStore.Label.OnAnimeItemClick -> onAnimeItemClick(it.id)
                }
            }
        }
    }

    override fun onAnimeItemClick(id: Int) {
        store.accept(CategoriesStore.Intent.OnAnimeItemClick(id))
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onAnimeItemClick") onAnimeItemClick: (Int) -> Unit,
        ): DefaultCategoriesComponent
    }
}