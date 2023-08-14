package com.miraelDev.hikari.presentation.VideoView

import com.miraelDev.hikari.domain.models.PlayerWrapper

sealed class VideoViewScreenState {

    object Loading : VideoViewScreenState()

    object Initial : VideoViewScreenState()

    data class Failure(val failure: Int) : VideoViewScreenState()

    data class Result(val result: PlayerWrapper) : VideoViewScreenState()
}