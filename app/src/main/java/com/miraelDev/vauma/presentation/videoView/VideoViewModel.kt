package com.miraelDev.vauma.presentation.videoView

import androidx.lifecycle.ViewModel
import androidx.media3.common.util.UnstableApi
import com.miraelDev.vauma.domain.usecases.animeDetailUseCase.GetAnimeDetailUseCase
import com.miraelDev.vauma.domain.usecases.videoPlayerUseCase.GetVideoPlayerUseCase
import com.miraelDev.vauma.domain.usecases.videoPlayerUseCase.LoadNextEpisodeUseCase
import com.miraelDev.vauma.domain.usecases.videoPlayerUseCase.LoadPreviousEpisodeUseCase
import com.miraelDev.vauma.domain.usecases.videoPlayerUseCase.LoadSpecificEpisodeUseCase
import com.miraelDev.vauma.domain.usecases.videoPlayerUseCase.LoadVideoIdUseCase
import com.miraelDev.vauma.domain.usecases.videoPlayerUseCase.LoadVideoPlayerUseCase
import com.miraelDev.vauma.domain.usecases.videoPlayerUseCase.ReleasePlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class VideoViewModel @Inject constructor(
        private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
        private val loadVideoIdUseCase: LoadVideoIdUseCase,
        private val loadPreviousEpisodeUseCase: LoadPreviousEpisodeUseCase,
        private val loadNextEpisodeUseCase: LoadNextEpisodeUseCase,
        private val loadSpecificEpisodeUseCase: LoadSpecificEpisodeUseCase,
        private val loadVideoPlayerUseCase: LoadVideoPlayerUseCase,
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

    //not read attention!!!!!!!!!!!!!!!!
    fun loadVideoSelectedQuality(quality: String) {
        //TODO
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayerUseCase()
    }
}