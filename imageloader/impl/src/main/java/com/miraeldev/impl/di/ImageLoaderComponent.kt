package com.miraeldev.impl.di

import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.impl.VaumaImageLoaderImpl
import me.tatarka.inject.annotations.Provides

interface ImageLoaderComponent {
    @Provides
    fun VaumaImageLoaderImpl.bind(): VaumaImageLoader = this
}