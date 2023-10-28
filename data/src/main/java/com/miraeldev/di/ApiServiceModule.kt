package com.miraeldev.di


import com.miraeldev.data.remote.searchApi.SearchApiService
import com.miraeldev.data.remote.searchApi.SearchApiServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ApiServiceModule {

    @Binds
    abstract fun bindSearchApiService(impl: SearchApiServiceImpl): SearchApiService

}