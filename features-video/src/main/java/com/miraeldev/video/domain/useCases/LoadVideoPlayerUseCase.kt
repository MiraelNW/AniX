package com.miraeldev.video.domain.useCases

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.video.data.repository.VideoPlayerRepository
import javax.inject.Inject

class LoadVideoPlayerUseCase @Inject constructor(val repository: VideoPlayerRepository) {
    operator fun invoke(animeInfo: AnimeDetailInfo) = repository.loadVideoPlayer(animeInfo)
}