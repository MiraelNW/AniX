package com.miraeldev.video.presentation.videoComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.OnBackPressed
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultVideoComponentFactory = (ComponentContext, OnBackPressed) -> DefaultVideoComponent

@Inject
class DefaultVideoComponent(
    private val storeFactory: VideoStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onBackClicked: () -> Unit,
) : VideoComponent, ComponentContext by componentContext {

    private val store: VideoStore = instanceKeeper.getStore { storeFactory.create() }
    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    VideoStore.Label.OnBackPressed -> onBackClicked()
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<VideoStore.State> = store.stateFlow

    override fun loadNextVideo() {
        store.accept(VideoStore.Intent.LoadNextVideo)
    }

    override fun loadPreviousVideo() {
        store.accept(VideoStore.Intent.LoadPreviousVideo)
    }

    override fun loadVideoSelectedQuality(quality: String) {
        store.accept(VideoStore.Intent.LoadVideoSelectedQuality(quality))
    }

    override fun loadSpecificEpisode(episodeId: Int) {
        store.accept(VideoStore.Intent.LoadSpecificEpisode(episodeId))
    }

    override fun onBackPressed() {
        store.accept(VideoStore.Intent.OnBackPressed)
    }
}