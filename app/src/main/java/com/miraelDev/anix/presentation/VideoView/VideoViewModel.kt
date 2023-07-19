package com.miraelDev.anix.presentation.VideoView

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.miraelDev.anix.domain.models.VideoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class VideoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val player: ExoPlayer
) : ViewModel() {


    private val videoUri = savedStateHandle.getStateFlow(VIDEO_URI, "")

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

    val exoPlayer = player
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

//    fun playVideo() {
//        player.setMediaItem(
//            videoItem.value.mediaItem ?: return
//        )
//    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }


    companion object {
        private const val VIDEO_URI = "videoUri"
        private const val PLAYER_SEEK_BACK_INCREMENT = 5 * 1000L // 5 seconds
        private const val PLAYER_SEEK_FORWARD_INCREMENT = 10 * 1000L // 10 seconds

    }

}