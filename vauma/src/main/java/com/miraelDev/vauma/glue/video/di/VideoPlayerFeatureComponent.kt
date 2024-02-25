package com.miraelDev.vauma.glue.video.di

import com.miraelDev.vauma.glue.video.repository.VideoPlayerRepositoryImpl
import com.miraeldev.video.data.repository.VideoPlayerRepository
import me.tatarka.inject.annotations.Provides

interface VideoPlayerFeatureComponent {

    @Provides
    fun VideoPlayerRepositoryImpl.bind(): VideoPlayerRepository = this

}