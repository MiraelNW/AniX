package com.miraelDev.hikari.di


import com.miraelDev.hikari.data.remote.searchApi.SearchApiService
import com.miraelDev.hikari.data.remote.searchApi.SearchApiServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiServiceModule {

    @Binds
    abstract fun bindSearchApiService(impl: SearchApiServiceImpl): SearchApiService

}