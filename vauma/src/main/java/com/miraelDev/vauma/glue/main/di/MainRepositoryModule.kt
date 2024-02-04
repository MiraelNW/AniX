package com.miraelDev.vauma.glue.main.di

import com.miraelDev.vauma.domain.repository.MainRepository
import com.miraelDev.vauma.glue.main.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MainRepositoryModule {

    @Binds
    fun bindMainRepository(impl: MainRepositoryImpl): MainRepository

}