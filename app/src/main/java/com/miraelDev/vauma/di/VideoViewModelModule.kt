package com.miraelDev.vauma.di

import android.app.Application
import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.miraelDev.vauma.data.repository.VideoPlayerRepositoryImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped


@UnstableApi
@Module
@InstallIn(ViewModelComponent::class)
object VideoViewModelModule {
}