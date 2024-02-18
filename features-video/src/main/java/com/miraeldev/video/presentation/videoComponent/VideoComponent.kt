package com.miraeldev.video.presentation.videoComponent

import kotlinx.coroutines.flow.StateFlow

interface VideoComponent {

    val model: StateFlow<VideoStore.State>

    fun loadNextVideo()
    fun loadPreviousVideo()
    fun loadVideoSelectedQuality(quality: String)
    fun loadSpecificEpisode(episodeId: Int)
    fun onBackPressed()
}