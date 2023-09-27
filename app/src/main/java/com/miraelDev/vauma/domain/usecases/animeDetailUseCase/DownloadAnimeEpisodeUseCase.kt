package com.miraelDev.vauma.domain.usecases.animeDetailUseCase

import com.miraelDev.vauma.domain.downloader.Downloader
import javax.inject.Inject

class DownloadAnimeEpisodeUseCase @Inject constructor(private val downloader: Downloader) {
    suspend operator fun invoke(url:String, videoName:String) = downloader.downloadVideo(url,videoName)
}