package com.miraelDev.hikari.di


import android.content.Context
import com.miraelDev.hikari.data.remote.searchApi.SearchApiService
import com.miraelDev.hikari.data.remote.searchApi.SearchApiServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiServiceModule {

    @Binds
    abstract fun bindSearchApiService(impl: SearchApiServiceImpl): SearchApiService

}