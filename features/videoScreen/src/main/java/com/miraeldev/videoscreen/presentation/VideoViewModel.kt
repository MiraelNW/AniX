package com.miraeldev.videoscreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.miraeldev.videoscreen.domain.useCases.GetVideoPlayerUseCase
import com.miraeldev.videoscreen.domain.useCases.LoadNextEpisodeUseCase
import com.miraeldev.videoscreen.domain.useCases.LoadPreviousEpisodeUseCase
import com.miraeldev.videoscreen.domain.useCases.LoadSpecificEpisodeUseCase
import com.miraeldev.videoscreen.domain.useCases.ReleasePlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class VideoViewModel @Inject constructor(
    private val loadPreviousEpisodeUseCase: LoadPreviousEpisodeUseCase,
    private val loadNextEpisodeUseCase: LoadNextEpisodeUseCase,
    private val loadSpecificEpisodeUseCase: LoadSpecificEpisodeUseCase,
    private val getVideoPlayerUseCase: GetVideoPlayerUseCase,
    private val releasePlayerUseCase: ReleasePlayerUseCase

) : ViewModel() {

    val screenState = getVideoPlayerUseCase()
//            .map {
//                when (val res = it) {
//
//                    is Result.Success -> {
//                        loadVideoPlayerUseCase(res.animeList.first())
//                        VideoViewScreenState.Result(result = getVideoPlayerUseCase()) as VideoViewScreenState
//                    }
//
//                    is Result.Failure -> {
//                        VideoViewScreenState.Failure(failure = res.failureCause) as VideoViewScreenState
//                    }
//
//                    else -> {
//                        VideoViewScreenState.Loading as VideoViewScreenState
//                    }
//
//                }
//            }


    fun loadNextVideo() {

        loadNextEpisodeUseCase()
    }

    fun loadPreviousVideo() {

        loadPreviousEpisodeUseCase()
    }

    fun loadSpecificEpisode(episodeId: Int) {

        loadSpecificEpisodeUseCase(episodeId)
    }

    //not ready attention!!!!!!!!!!!!!!!!
    fun loadVideoSelectedQuality(quality: String) {
        //TODO
    }

    fun releasePlayer() {
        viewModelScope.launch {
            releasePlayerUseCase()
        }
    }

}