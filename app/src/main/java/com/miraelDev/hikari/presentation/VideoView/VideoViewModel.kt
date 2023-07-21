package com.miraelDev.hikari.presentation.VideoView

import android.os.CountDownTimer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.miraelDev.hikari.presentation.VideoView.utilis.formatMinSec
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class VideoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val player: ExoPlayer
) : ViewModel() {


    private val videoUri = savedStateHandle.getStateFlow(VIDEO_URI, "")

    val currTime: StateFlow<String> get() = _currTime.asStateFlow()
    private val _currTime = MutableStateFlow("")

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
    fun startTimer(duration:Long){
        object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _currTime.value  = (duration - millisUntilFinished ).formatMinSec()
//                Log.d("tag",_currTime.value)
//                Log.d("tag",(duration - millisUntilFinished).formatMinSec())
            }

            override fun onFinish() {
            }

        }.start()
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