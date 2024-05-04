package com.miraeldev.favourites.presentation.favouriteComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.NavigateToSearchScreen
import com.miraeldev.models.OnAnimeItemClick
import com.miraeldev.models.anime.AnimeInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultFavouriteComponentFactory =
    (ComponentContext, OnAnimeItemClick, NavigateToSearchScreen) -> DefaultFavouriteComponent

@Inject
class DefaultFavouriteComponent(
    private val storeFactory: FavouriteStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onAnimeItemClick: (Int) -> Unit,
    @Assisted navigateToSearchScreen: (String) -> Unit
) : FavouriteComponent, ComponentContext by componentContext {

    private val store: FavouriteStore = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<FavouriteStore.State> = store.stateFlow

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is FavouriteStore.Label.OnAnimeItemClicked -> {
                        onAnimeItemClick(it.id)
                    }

                    is FavouriteStore.Label.NavigateToSearchScreen -> {
                        navigateToSearchScreen(it.search)
                    }
                }
            }
        }
    }

    override fun onAnimeItemClick(id: Int) {
        store.accept(FavouriteStore.Intent.OnAnimeItemClick(id))
    }

    override fun navigateToSearchScreen(search: String) {
        store.accept(FavouriteStore.Intent.NavigateToSearchScreen(search))
    }

    override fun updateSearchTextState(search: String) {
        store.accept(FavouriteStore.Intent.UpdateSearchTextState(search))
    }

    override fun selectAnimeItem(animeInfo: AnimeInfo) {
        store.accept(FavouriteStore.Intent.SelectAnimeItem(animeInfo))
    }

    override fun searchAnimeItemInDatabase(name: String) {
        store.accept(FavouriteStore.Intent.SearchAnimeItemInDatabase(name))
    }

    override fun searchAnimeByName(name: String) {
        store.accept(FavouriteStore.Intent.SearchAnimeByName(name))
    }

    override fun loadAnimeList() {
        store.accept(FavouriteStore.Intent.LoadAnimeList)
    }
}