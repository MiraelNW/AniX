package com.miraeldev.video.domain.useCases

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.video.data.repository.VideoPlayerRepository
import me.tatarka.inject.annotations.Inject

@Inject
class LoadVideoPlayerUseCase  constructor(val repository: VideoPlayerRepository) {
    operator fun invoke(animeInfo: AnimeDetailInfo) = repository.loadVideoPlayer(animeInfo)
}