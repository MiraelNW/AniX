package com.miraeldev.detailinfo.domain.useCases

import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import javax.inject.Inject

class DownloadAnimeEpisodeUseCase @Inject constructor(private val repository: AnimeDetailRepository) {
    suspend operator fun invoke(url:String, videoName:String) = repository.downloadVideo(url,videoName)
}