package com.miraelDev.vauma.glue.home.di

import com.miraelDev.vauma.glue.home.repository.HomeRepositoryImpl
import com.miraeldev.animelist.data.HomeRepository
import com.miraeldev.models.di.scope.Singleton
import me.tatarka.inject.annotations.Provides

interface HomeFeatureComponent {

    @Singleton
    @Provides
    fun HomeRepositoryImpl.bind(): HomeRepository = this
}