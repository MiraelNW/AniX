package com.miraelDev.vauma.glue.detailInfo.repositories

import com.miraeldev.AnimeDetailDataRepository
import com.miraeldev.Downloader
import com.miraeldev.VideoPlayerDataRepository
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.anime.toAnimeInfo
import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import com.miraeldev.result.ResultAnimeDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeDetailInfoRepositoryImpl @Inject constructor(
    private val animeDetailDataRepository: AnimeDetailDataRepository,
    private val videoPlayerDataRepository: VideoPlayerDataRepository,
    private val downloader: Downloader,
) : AnimeDetailRepository {
    override fun getAnimeDetail(): Flow<ResultAnimeDetail> {
        return animeDetailDataRepository.getAnimeDetail()
    }

    override suspend fun loadAnimeDetail(animeId: Int) {
        animeDetailDataRepository.loadAnimeDetail(animeId)
    }

    override suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeDetailInfo) {
        animeDetailDataRepository.selectAnimeItem(isSelected, animeInfo.toAnimeInfo())
    }

    override suspend fun downloadVideo(url: String, videoName: String) {
        downloader.downloadVideo(url, videoName)
    }

    override fun loadVideoId(videoId: Int) {
        videoPlayerDataRepository.loadVideoId(videoId)
    }
}