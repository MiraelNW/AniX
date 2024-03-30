package com.miraelDev.vauma.di

import com.miraelDev.vauma.domain.repository.MainRepository
import com.miraelDev.vauma.glue.main.repository.MainRepositoryImpl
import me.tatarka.inject.annotations.Provides

interface MainFeatureComponent {
    @Provides
    fun MainRepositoryImpl.bind(): MainRepository = this
}