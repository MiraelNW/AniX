package com.miraeldev.animelist.presentation.categories.categoriesComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.OnAnimeItemClick
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultCategoriesComponentFactory = (ComponentContext, OnAnimeItemClick) -> DefaultCategoriesComponent

@Inject
class DefaultCategoriesComponent(
    private val storeFactory: CategoriesStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onAnimeItemClick: (Int) -> Unit,
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
}