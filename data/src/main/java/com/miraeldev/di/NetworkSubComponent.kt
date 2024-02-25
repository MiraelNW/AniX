package com.miraeldev.di

import android.util.Log
import com.miraeldev.data.BuildConfig
import com.miraeldev.data.remote.searchApi.SearchApiService
import com.miraeldev.data.remote.searchApi.SearchApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides


typealias AuthHttpClient = HttpClient
typealias AppHttpClient = HttpClient


interface NetworkSubComponent {
    @Provides
    fun SearchApiServiceImpl.bind(): SearchApiService = this

    @Provides
    fun pupa(): HttpClient = HttpClient()


//    @Provides
//    fun provideAppNetworkClient(
//        localTokenService: LocalTokenService,
//        userDataRepository: LocalUserDataRepository
//    ): HttpClient = AppNetworkClient.createClient()
}