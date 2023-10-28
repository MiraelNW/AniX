package com.miraeldev.detailinfo.domain.useCases

import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import javax.inject.Inject

class LoadVideoIdUseCase @Inject constructor(val repository: AnimeDetailRepository) {
    operator fun invoke(videoId:Int) = repository.loadVideoId(videoId)
}