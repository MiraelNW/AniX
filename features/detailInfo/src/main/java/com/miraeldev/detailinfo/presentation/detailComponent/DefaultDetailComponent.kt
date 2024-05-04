package com.miraeldev.detailinfo.presentation.detailComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.OnAnimeItemClick
import com.miraeldev.models.OnBackPressed
import com.miraeldev.models.OnSeriesClick
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultDetailComponentFactory =
    (ComponentContext, OnBackPressed, OnSeriesClick, OnAnimeItemClick) -> DefaultDetailComponent

@Inject
class DefaultDetailComponent(
    private val storeFactory: DetailStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onBackClicked: () -> Unit,
    @Assisted onSeriesClick: () -> Unit,
    @Assisted onAnimeItemClick: (Int) -> Unit
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
}