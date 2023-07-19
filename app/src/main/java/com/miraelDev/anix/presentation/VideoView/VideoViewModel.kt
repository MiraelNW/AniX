package com.miraelDev.anix.presentation.VideoView

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.miraelDev.anix.domain.models.VideoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val application: Application
) : AndroidViewModel(application) {


    private val videoUri = savedStateHandle.getStateFlow(VIDEO_URI, "")

    val exoPlayer = ExoPlayer.Builder(application)
        .apply {
            setSeekBackIncrementMs(PLAYER_SEEK_BACK_INCREMENT)
            setSeekForwardIncrementMs(PLAYER_SEEK_FORWARD_INCREMENT)
        }
        .build()
        .apply {
            setMediaItem(
                MediaItem.Builder()
                    .apply {
                        setUri(
                            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                        )
                        setMediaMetadata(
                            MediaMetadata.Builder()
                                .setDisplayTitle("My Video")
                                .build()
                        )
                    }
                    .build()
            )
            prepare()
            playWhenReady = true
        }
//    val videoItem = videoUri.map { uri ->
//        VideoInfo(
//            playerUrl = uri,
//            mediaItem = MediaItem.Builder()
//                .apply {
//                    setUri(
//                        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
//                    )
//                    setMediaMetadata(
//                        MediaMetadata.Builder()
//                            .setDisplayTitle("My Video")
//                            .build()
//                    )
//                }
//                .build(),
//        )
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VideoInfo())
//
//    init {
//        playVideo()
//    }
//
//    fun playVideo() {
//        exoPlayer.setMediaItem(
//            videoItem.value.mediaItem ?: return
//        )
//        exoPlayer.prepare()
//        exoPlayer.playWhenReady = true
//    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
        Log.d("tag","cleared")
    }


    companion object {
        private const val VIDEO_URI = "videoUri"
        private const val PLAYER_SEEK_BACK_INCREMENT = 5 * 1000L // 5 seconds
        private const val PLAYER_SEEK_FORWARD_INCREMENT = 10 * 1000L // 10 seconds

    }

}