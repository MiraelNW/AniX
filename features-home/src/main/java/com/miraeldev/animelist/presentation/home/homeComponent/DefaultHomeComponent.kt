package com.miraeldev.animelist.presentation.home.homeComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.extensions.componentScope
import com.miraeldev.user.User
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultHomeComponent @AssistedInject internal constructor(
    private val homeStoreFactory: HomeStoreFactory,
    @Assisted("onAnimeItemClick") onAnimeItemClick: (Int) -> Unit,
    @Assisted("onSeeAllClick") onSeeAllClick: (Int) -> Unit,
    @Assisted("onPlayClick") onPlayClick: (Int) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
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

    override fun addAnimeToList(user: User, isSelected: Boolean, animeItem: LastWatchedAnime) {
        store.accept(HomeStore.Intent.AddAnimeToList(user, isSelected, animeItem))
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

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onAnimeItemClick") onAnimeItemClick: (Int) -> Unit,
            @Assisted("onSeeAllClick") onSeeAllClick: (Int) -> Unit,
            @Assisted("onPlayClick") onPlayClick: (Int) -> Unit,
        ): DefaultHomeComponent
    }
}