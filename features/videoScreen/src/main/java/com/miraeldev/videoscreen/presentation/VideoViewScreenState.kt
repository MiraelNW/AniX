package com.miraeldev.videoscreen.presentation

import com.miraeldev.videoscreen.domain.models.PlayerWrapper

internal sealed class VideoViewScreenState {

    data object Loading : VideoViewScreenState()

    data object Initial : VideoViewScreenState()

    data class Failure(val failure: Int) : VideoViewScreenState()

    data class Result(val result: PlayerWrapper) : VideoViewScreenState()
}