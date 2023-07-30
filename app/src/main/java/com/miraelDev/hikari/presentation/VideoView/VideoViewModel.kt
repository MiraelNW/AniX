package com.miraelDev.hikari.presentation.VideoView

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide.init
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.usecases.GetAnimeDetailUseCase
import com.miraelDev.hikari.domain.usecases.GetVideoIdUseCase
import com.miraelDev.hikari.domain.usecases.LoadVideoIdUseCase
import com.miraelDev.hikari.presentation.VideoView.utilis.formatMinSec
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    private val loadVideoIdUseCase: LoadVideoIdUseCase,
    private val getVideoIdUseCase: GetVideoIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    val player: ExoPlayer
) : ViewModel() {


    private val videoUrlState = savedStateHandle.getStateFlow(VIDEO_URI, "")

    private val timer = object : CountDownTimer(3 * 60 * 60 * 1000, 1000){
        override fun onTick(millisUntilFinished: Long) {
            _currTime.value = player.currentPosition.formatMinSec()
        }

        override fun onFinish() {
            TODO("Not yet implemented")
        }
    }



    val currTime: StateFlow<String> get() = _currTime.asStateFlow()
    private val _currTime = MutableStateFlow("")

    private val animeDetail = getAnimeDetailUseCase().value
    private val videoId = getVideoIdUseCase()

    val isFirstEpisode = MutableStateFlow(videoId.value == 0)
    val isLastEpisode =
        MutableStateFlow(videoId.value == (animeDetail.videoUrls.playerUrl.size - 1))

    init {
        val url = animeDetail.videoUrls.playerUrl[videoId.value]
        val name = animeDetail.videoUrls.videoName[videoId.value]

        if (url != videoUrlState.value) {
            setMediaItem(url, name)
        }
        savedStateHandle[VIDEO_URI] = url
        player.prepare()

//        startTimer()
    }

    fun startTimer() {
        timer.start()
    }

    fun stopTimer() {
        timer.cancel()
    }

    fun seekToChangeCurrTime(time:String){
        _currTime.value = time
    }

    fun loadNextVideo() {

        val currentVideoIndex = videoId.value
        if (currentVideoIndex == (animeDetail.videoUrls.playerUrl.size - 1)) return

        val url = animeDetail.videoUrls.playerUrl[currentVideoIndex + 1]
        val name = animeDetail.videoUrls.videoName[currentVideoIndex + 1]

        setMediaItem(url, name)

        isFirstEpisode.value = false
        isLastEpisode.value = currentVideoIndex + 1 == (animeDetail.videoUrls.playerUrl.size - 1)

        loadVideoIdUseCase(currentVideoIndex + 1)
        savedStateHandle[VIDEO_URI] = url
    }

    fun loadPreviousVideo() {

        val currentVideoIndex = videoId.value

        if (currentVideoIndex == 0) return

        val url = animeDetail.videoUrls.playerUrl[currentVideoIndex - 1]
        val name = animeDetail.videoUrls.videoName[currentVideoIndex - 1]

        setMediaItem(url, name)

        isFirstEpisode.value = currentVideoIndex - 1 == 0
        isLastEpisode.value = false

        loadVideoIdUseCase(currentVideoIndex - 1)
        savedStateHandle[VIDEO_URI] = url
    }

    fun loadSpecificEpisode(episodeId: Int) {
        val currentVideoIndex = videoId.value

        if (currentVideoIndex == episodeId) return

        val url = animeDetail.videoUrls.playerUrl[episodeId]
        val name = animeDetail.videoUrls.videoName[episodeId]

        setMediaItem(url, name)

        isFirstEpisode.value = episodeId == 0
        isLastEpisode.value = episodeId == (animeDetail.videoUrls.playerUrl.size - 1)

        loadVideoIdUseCase(episodeId)
        savedStateHandle[VIDEO_URI] = url
    }

    //not read attention!!!!!!!!!!!!!!!!
    fun loadVideoSelectedQuality(quality: String) {
        //TODO
    }

    private fun setMediaItem(url: String, name: String) {
        player.apply {
            setMediaItem(
                MediaItem.Builder()
                    .apply {
                        setUri(url)
                        setMediaMetadata(
                            MediaMetadata.Builder()
                                .setDisplayTitle(name)
                                .build()
                        )
                    }
                    .build()
            )
            playWhenReady = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
        timer.cancel()
    }

    companion object {
        private const val VIDEO_URI = "videoUri"
    }

}