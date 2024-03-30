package com.miraeldev.imageloader.di

import com.miraeldev.imageloader.VaumaImageLoader
import com.miraeldev.imageloader.VaumaImageLoaderImpl
import me.tatarka.inject.annotations.Provides

interface ImageLoaderComponent {
    @Provides
    fun VaumaImageLoaderImpl.bind(): VaumaImageLoader = this
}