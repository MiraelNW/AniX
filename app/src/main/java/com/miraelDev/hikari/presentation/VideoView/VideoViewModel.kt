package com.miraelDev.hikari.presentation.VideoView

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.usecases.GetAnimeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    private val savedStateHandle: SavedStateHandle,
    val player: ExoPlayer
) : ViewModel() {


    private val videoUrlState = savedStateHandle.getStateFlow(VIDEO_URI, "")
    private val videoIndexState = savedStateHandle.getStateFlow(VIDEO_INDEX, 0)

    val currTime: StateFlow<String> get() = _currTime.asStateFlow()
    private val _currTime = MutableStateFlow("")

    private var animeDetail = AnimeInfo(0)

    init {
        player.prepare()
    }

    fun getAnimeDetail(id: Int) {
        animeDetail = getAnimeDetailUseCase(id)
    }

    fun setUrl(videoIndex: Int) {
        val url = animeDetail.videoUrls.playerUrl[videoIndex]
        val name = animeDetail.videoUrls.videoName[videoIndex]
        if (url != videoUrlState.value) {
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
        savedStateHandle[VIDEO_URI] = url
//        if(videoIndexState.value != videoIndex){
//            savedStateHandle[VIDEO_INDEX] = videoIndex
//        }

    }

    fun loadNextVideo() {
        val currentVideoIndex = videoIndexState.value

        savedStateHandle[VIDEO_INDEX] = (currentVideoIndex + 1)
        val url = animeDetail.videoUrls.playerUrl[currentVideoIndex + 1]
        val name = animeDetail.videoUrls.videoName[currentVideoIndex + 1]
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

        savedStateHandle[VIDEO_URI] = url
    }

    //not read attention!!!!!!!!!!!!!!!!
    fun loadPreviousVideo() {
        val currentVideoIndex = videoIndexState.value

        savedStateHandle[VIDEO_INDEX] = (currentVideoIndex + 1)
        val url = animeDetail.videoUrls.playerUrl[currentVideoIndex + 1]
        val name = animeDetail.videoUrls.videoName[currentVideoIndex + 1]
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

        savedStateHandle[VIDEO_URI] = url
    }

    //not read attention!!!!!!!!!!!!!!!!
    fun loadSpecificEpisode(episodeId: Int){

    }

    //not read attention!!!!!!!!!!!!!!!!
    fun loadVideoSelectedQuality( quality: String){

    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }


    companion object {
        private const val VIDEO_URI = "videoUri"
        private const val VIDEO_INDEX = "videoIndex"
    }

}