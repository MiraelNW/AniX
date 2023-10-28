package com.miraeldev.data.repository

import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.miraeldev.VideoPlayerDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.anime.VideoInfo
import com.miraeldev.domain.models.PlayerWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@UnstableApi
internal class VideoPlayerRepositoryImpl @Inject constructor(
    private val player: ExoPlayer
) : VideoPlayerDataRepository {

    private val videoUrls = MutableStateFlow(VideoInfo())
    private val videoId = MutableStateFlow(0)

    private val playerWrapper = MutableStateFlow(
        PlayerWrapper(player, isFirstEpisode = false, isLastEpisode = false, title = "no name")
    )

    override fun getVideoPlayer(): StateFlow<PlayerWrapper> {
        player.prepare()
        return playerWrapper.asStateFlow()
    }

    override fun loadVideoId(id: Int) {
        videoId.value = id

        playerWrapper.value =
            playerWrapper.value.copy(isLastEpisode = false, isFirstEpisode = false)

        if (id == (videoUrls.value.playerUrl.size - 1)) {
            playerWrapper.value = playerWrapper.value.copy(isLastEpisode = true)
        }
        if (id == 0) {
            playerWrapper.value = playerWrapper.value.copy(isFirstEpisode = true)
        }

        val name = videoUrls.value.videoName[videoId.value]
        val url = videoUrls.value.playerUrl[videoId.value]

        setMediaItem(name, url)
    }

    override fun loadVideoPlayer(animeInfo: AnimeInfo) {
        videoUrls.value = animeInfo.videoUrls

        val name = videoUrls.value.videoName[videoId.value]
        val url = videoUrls.value.playerUrl[videoId.value]

        setMediaItem(name, url)
    }

    override fun loadNextEpisode() {
        val nextVideoId = videoId.value + 1
        videoId.value = nextVideoId

        playerWrapper.value =
            playerWrapper.value.copy(isLastEpisode = false, isFirstEpisode = false)

        if (nextVideoId == (videoUrls.value.playerUrl.size - 1)) {
            playerWrapper.value = playerWrapper.value.copy(isLastEpisode = true)
        }

        val name = videoUrls.value.videoName[nextVideoId]
        val url = videoUrls.value.playerUrl[nextVideoId]

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

        val name = videoUrls.value.videoName[previousVideoId]
        val url = videoUrls.value.playerUrl[previousVideoId]

        setMediaItem(name, url)
    }

    override fun releasePlayer() {
        player.stop()
    }

    override fun loadSpecificEpisode(specificEpisode: Int) {

        if (specificEpisode == videoId.value) return

        videoId.value = specificEpisode

        playerWrapper.value =
            playerWrapper.value.copy(isLastEpisode = false, isFirstEpisode = false)

        if (specificEpisode == 0) {
            playerWrapper.value = playerWrapper.value.copy(isFirstEpisode = true)
        }

        if (specificEpisode == (videoUrls.value.playerUrl.size - 1)) {
            playerWrapper.value = playerWrapper.value.copy(isLastEpisode = true)
        }

        val name = videoUrls.value.videoName[specificEpisode]
        val url = videoUrls.value.playerUrl[specificEpisode]

        setMediaItem(name, url)
    }

    private fun setMediaItem(name: String, url: String) {
        player.apply {
            setMediaItem(
                MediaItem.Builder()
                    .setUri(url)
                    .build()
            )
            playWhenReady = true
        }
        playerWrapper.value = playerWrapper.value.copy(title = name)
    }
}