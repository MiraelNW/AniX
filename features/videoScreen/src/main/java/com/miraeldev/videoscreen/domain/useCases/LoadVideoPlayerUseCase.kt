package com.miraeldev.videoscreen.domain.useCases

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.videoscreen.data.repository.VideoPlayerRepository
import javax.inject.Inject

class LoadVideoPlayerUseCase @Inject constructor(val repository: VideoPlayerRepository) {
    operator fun invoke(animeInfo: AnimeInfo) = repository.loadVideoPlayer(animeInfo)
}