package com.miraeldev.animelist.presentation.home.homeComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.OnAnimeItemClick
import com.miraeldev.models.OnPlayClick
import com.miraeldev.models.OnSeeAllClick
import com.miraeldev.models.anime.LastWatchedAnime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultHomeComponentFactory = (ComponentContext, OnAnimeItemClick, OnSeeAllClick, OnPlayClick) ->
DefaultHomeComponent

@Inject
class DefaultHomeComponent(
    private val homeStoreFactory: HomeStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onAnimeItemClick: (Int) -> Unit,
    @Assisted onSeeAllClick: (Int) -> Unit,
    @Assisted onPlayClick: (Int) -> Unit
) : HomeComponent, ComponentContext by componentContext {

    private val store: HomeStore = instanceKeeper.getStore { homeStoreFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is HomeStore.Label.OnSeeAllClick -> onSeeAllClick(it.id)
                    is HomeStore.Label.OnAnimeItemClick -> onAnimeItemClick(it.id)
                    is HomeStore.Label.OnPlayClick -> onPlayClick(it.id)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<HomeStore.State>
        get() = store.stateFlow

    override fun loadAnimeVideo(anime: LastWatchedAnime) {
        store.accept(HomeStore.Intent.LoadAnimeVideo(anime))
    }

    override fun addAnimeToList(isSelected: Boolean, animeItem: LastWatchedAnime) {
        store.accept(HomeStore.Intent.AddAnimeToList(isSelected, animeItem))
    }

    override fun onAnimeItemClick(id: Int) {
        store.accept(HomeStore.Intent.OnAnimeItemClick(id))
    }

    override fun onSeeAllClick(id: Int) {
        store.accept(HomeStore.Intent.OnSeeAllClick(id))
    }

    override fun onPlayClick(id: Int) {
        store.accept(HomeStore.Intent.OnPlayClick(id))
    }
}
