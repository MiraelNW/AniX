package com.miraelDev.vauma.presentation.videoView

import com.miraelDev.vauma.domain.models.PlayerWrapper

sealed class VideoViewScreenState {

    object Loading : VideoViewScreenState()

    object Initial : VideoViewScreenState()

    data class Failure(val failure: Int) : VideoViewScreenState()

    data class Result(val result: PlayerWrapper) : VideoViewScreenState()
}