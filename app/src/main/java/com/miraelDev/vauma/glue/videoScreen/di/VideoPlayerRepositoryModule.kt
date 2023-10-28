package com.miraelDev.vauma.glue.videoScreen.di

import com.miraelDev.vauma.glue.videoScreen.repository.VideoPlayerRepositoryImpl
import com.miraeldev.videoscreen.data.repository.VideoPlayerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface VideoPlayerRepositoryModule {

    @Binds
    fun bindVideoPlayerRepository(impl: VideoPlayerRepositoryImpl): VideoPlayerRepository

}