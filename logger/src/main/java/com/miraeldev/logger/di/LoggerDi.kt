package com.miraeldev.logger.di

import com.miraeldev.logger.LogTracker
import com.miraeldev.logger.LoggerApi
import com.miraeldev.logger.impl.CrashlyticsLogTracker
import com.miraeldev.logger.impl.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LoggerDi {

    @Binds
    @Singleton
    abstract fun bindLogger(impl: LoggerImpl): LoggerApi

    companion object {
        @Provides
        fun provideLogTracker():LogTracker {
            return CrashlyticsLogTracker()
        }
    }
}