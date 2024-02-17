package com.miraeldev.detailinfo.presentation.detailComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.extensions.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DefaultDetailComponent @AssistedInject constructor(
    private val storeFactory: DetailStoreFactory,
    @Assisted("onBackClicked") onBackClicked:()->Unit,
    @Assisted("onSeriesClick") onSeriesClick:()->Unit,
    @Assisted("onAnimeItemClick") onAnimeItemClick:(Int)->Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : DetailComponent, ComponentContext by componentContext {

    private val store: DetailStore = instanceKeeper.getStore { storeFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is DetailStore.Label.OnBackClicked -> onBackClicked()
                    is DetailStore.Label.OnSeriesClick -> onSeriesClick()
                    is DetailStore.Label.OnAnimeItemClick -> onAnimeItemClick(it.id)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<DetailStore.State> = store.stateFlow

    override fun onBackClicked() {
        store.accept(DetailStore.Intent.OnBackClicked)
    }

    override fun loadAnimeDetail(id: Int) {
        store.accept(DetailStore.Intent.LoadAnimeDetail(id))
    }

    override fun downloadEpisode(url: String, videoName: String) {
        store.accept(DetailStore.Intent.DownloadEpisode(url, videoName))
    }

    override fun loadAnimeVideo(animeItem: AnimeDetailInfo, videoId: Int) {
        store.accept(DetailStore.Intent.LoadAnimeVideo(animeItem, videoId))
    }

    override fun onAnimeItemClick(id: Int) {
        store.accept(DetailStore.Intent.OnAnimeItemClick(id))
    }

    override fun onSeriesClick() {
        store.accept(DetailStore.Intent.OnSeriesClick)
    }

    override fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeDetailInfo) {
        store.accept(DetailStore.Intent.SelectAnimeItem(isSelected, animeInfo))
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onBackClicked") onBackClicked:()->Unit,
            @Assisted("onSeriesClick") onSeriesClick:()->Unit,
            @Assisted("onAnimeItemClick") onAnimeItemClick:(Int)->Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ):DefaultDetailComponent
    }
}