package com.miraeldev.logger.di

import com.miraeldev.logger.LogTracker
import com.miraeldev.logger.LoggerApi
import com.miraeldev.logger.impl.CrashlyticsLogTracker
import com.miraeldev.logger.impl.LoggerImpl
import me.tatarka.inject.annotations.Provides

interface LoggerFeature {

//    @Singleton
    @Provides
    fun LoggerImpl.bind(): LoggerApi = this

    @Provides
//    @Singleton
    fun provideLogTracker(): LogTracker = CrashlyticsLogTracker()
}