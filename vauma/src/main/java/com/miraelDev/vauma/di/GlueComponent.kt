package com.miraelDev.vauma.di

import android.content.Context
import com.miraelDev.vauma.domain.repository.MainRepository
import com.miraeldev.di.DataComponent
import com.miraeldev.di.scope.Singleton
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

interface GlueComponent: DataComponent {
    @Singleton
    fun provideMainRepository(): MainRepository
}