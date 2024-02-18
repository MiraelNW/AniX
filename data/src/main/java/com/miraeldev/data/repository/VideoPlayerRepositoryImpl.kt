package com.miraeldev.data.repository

import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import com.miraeldev.UserDataRepository
import com.miraeldev.VideoPlayerDataRepository
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.anime.VideoInfo
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.video.PlayerWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@UnstableApi
internal class VideoPlayerRepositoryImpl @Inject constructor(
    private val userDataRepository: UserDataRepository
) : VideoPlayerDataRepository {

    private val videoModel = MutableStateFlow(listOf(VideoInfo()))
    private val videoId = MutableStateFlow(0)

    private var lastWatchedAnime = LastWatchedAnime(-1)

    private val playerWrapper = MutableStateFlow(
        PlayerWrapper("", isFirstEpisode = false, isLastEpisode = false, title = "no name")
    )

    override fun getVideoInfo(): StateFlow<PlayerWrapper> = playerWrapper.asStateFlow()

    override fun loadVideoId(animeItem: AnimeDetailInfo, videoId: Int) {
        this.videoId.value = videoId
        videoModel.value = animeItem.videos.toList()

        playerWrapper.value =
            playerWrapper.value.copy(isLastEpisode = false, isFirstEpisode = false)

        if (videoId == (videoModel.value.size - 1)) {
            playerWrapper.value = playerWrapper.value.copy(isLastEpisode = true)
        }
        if (videoId == 0) {
            playerWrapper.value = playerWrapper.value.copy(isFirstEpisode = true)
        }

        val name = videoModel.value[this.videoId.value].videoName
        val url = videoModel.value[this.videoId.value].videoUrl480

        lastWatchedAnime = lastWatchedAnime.copy(
            id = animeItem.id,
            imageUrl = animeItem.image.original,
            nameEn = animeItem.nameEn,
            nameRu = animeItem.nameRu,
            genres = animeItem.genres,
            isFavourite = animeItem.isFavourite,
            episodeNumber = this.videoId.value,
            video = videoModel.value[this.videoId.value]
        )

        setMediaItem(name, url)
    }

    override fun loadVideoPlayer(animeInfo: AnimeDetailInfo) {
        videoModel.value = animeInfo.videos

        val name = videoModel.value[videoId.value].videoName
        val url = videoModel.value[videoId.value].videoUrl480

        setMediaItem(name, url)
    }

    override fun loadNextEpisode() {
        val nextVideoId = videoId.value + 1
        videoId.value = nextVideoId

        playerWrapper.value =
            playerWrapper.value.copy(isLastEpisode = false, isFirstEpisode = false)

        if (nextVideoId == (videoModel.value.size - 1)) {
            playerWrapper.value = playerWrapper.value.copy(isLastEpisode = true)
        }

        val name = videoModel.value[nextVideoId].videoName
        val url = videoModel.value[nextVideoId].videoUrl1080

        lastWatchedAnime = lastWatchedAnime.copy(
            episodeNumber = nextVideoId,
            video = videoModel.value[nextVideoId]
        )

        setMediaItem(name, url)
    }

    override fun loadPrevEpisode() {
        val previousVideoId = videoId.value - 1
        videoId.value = previousVideoId

        playerWrapper.value =
            playerWrapper.value.copy(isLastEpisode = false, isFirstEpisode = false)

        if (previousVideoId == 0) {
            playerWrapper.value = playerWrapper.value.copy(isFirstEpisode = true)
        }

        val name = videoModel.value[previousVideoId].videoName
        val url = videoModel.value[previousVideoId].videoUrl480

        lastWatchedAnime = lastWatchedAnime.copy(
            episodeNumber = previousVideoId,
            video = videoModel.value[previousVideoId]
        )

        setMediaItem(name, url)
    }

    override suspend fun releasePlayer() {
        userDataRepository.saveLastWatchedAnime(lastWatchedAnime)
    }

    override fun loadSpecificEpisode(specificEpisode: Int) {

        if (specificEpisode == videoId.value) return

        videoId.value = specificEpisode

        playerWrapper.value =
            playerWrapper.value.copy(isLastEpisode = false, isFirstEpisode = false)

        if (specificEpisode == 0) {
            playerWrapper.value = playerWrapper.value.copy(isFirstEpisode = true)
        }

        if (specificEpisode == (videoModel.value.size - 1)) {
            playerWrapper.value = playerWrapper.value.copy(isLastEpisode = true)
        }

        val name = videoModel.value[specificEpisode].videoName
        val url = videoModel.value[specificEpisode].videoUrl480

        lastWatchedAnime = lastWatchedAnime.copy(
            episodeNumber = specificEpisode,
            video = videoModel.value[specificEpisode]
        )

        setMediaItem(name, url)
    }

    private fun setMediaItem(name: String, url: String) {
        playerWrapper.value = playerWrapper.value.copy(link = url, title = name)
    }
}