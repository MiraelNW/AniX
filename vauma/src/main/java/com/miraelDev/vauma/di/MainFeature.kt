package com.miraelDev.vauma.di

import com.miraelDev.vauma.domain.repository.MainRepository
import com.miraelDev.vauma.glue.main.repository.MainRepositoryImpl
import me.tatarka.inject.annotations.Provides

interface MainFeature {
    @Provides
    fun MainRepositoryImpl.bind(): MainRepository = this
}