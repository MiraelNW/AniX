package com.miraeldev.di

import com.miraeldev.data.network.AppNetworkClient
import com.miraeldev.data.network.AppNetworkClientImpl
import com.miraeldev.data.network.AuthNetworkClient
import com.miraeldev.data.network.AuthNetworkClientImpl
import com.miraeldev.data.remote.searchApi.SearchApiService
import com.miraeldev.data.remote.searchApi.SearchApiServiceImpl
import me.tatarka.inject.annotations.Provides


interface NetworkSubComponent {
    @Provides
    fun SearchApiServiceImpl.bind(): SearchApiService = this

    @Provides
    fun AppNetworkClientImpl.bind() : AppNetworkClient = this
    @Provides
    fun AuthNetworkClientImpl.bind() : AuthNetworkClient = this
}