package com.miraeldev.detailinfo.domain.useCases

import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import me.tatarka.inject.annotations.Inject

@Inject
class DownloadAnimeEpisodeUseCase(private val repository: AnimeDetailRepository) {
    suspend operator fun invoke(url:String, videoName:String) = repository.downloadVideo(url,videoName)
}