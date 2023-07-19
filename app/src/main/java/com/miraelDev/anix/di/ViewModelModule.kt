package com.miraelDev.anix.di

import android.app.Application
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@UnstableApi @Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    private const val PLAYER_SEEK_BACK_INCREMENT = 10 * 1000L // 5 seconds
    private const val PLAYER_SEEK_FORWARD_INCREMENT = 10 * 1000L // 10 seconds
    @Provides
    @ViewModelScoped
    fun provideVideoPlayer(application: Application): ExoPlayer {
        return ExoPlayer.Builder(application)
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
                        .build(),
                )
                prepare()
            }
    }

}