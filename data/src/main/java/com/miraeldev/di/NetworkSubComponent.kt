package com.miraeldev.di

import com.miraeldev.LocalUserDataRepository
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.network.AppNetworkClient
import com.miraeldev.data.network.AuthNetworkClient
import com.miraeldev.data.remote.searchApi.SearchApiService
import com.miraeldev.di.scope.Singleton
import io.ktor.client.HttpClient
import me.tatarka.inject.annotations.Provides


typealias AuthHttpClient = HttpClient
typealias AppHttpClient = HttpClient


interface NetworkSubComponent {

    @Singleton
    val provideSearchApiService: SearchApiService

    @Provides
    fun provideAuthNetworkClient(): AuthHttpClient = AuthNetworkClient.createClient()

    @Provides
    fun provideAppNetworkClient(
        localTokenService: LocalTokenService,
        userDataRepository: LocalUserDataRepository
    ): HttpClient = AppNetworkClient.createClient()
}