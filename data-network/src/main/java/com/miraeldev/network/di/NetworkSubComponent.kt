package com.miraeldev.network.di

import com.miraeldev.models.di.scope.Singleton
import com.miraeldev.network.impl.AppNetworkClientImpl
import com.miraeldev.network.AuthNetworkClient
import com.miraeldev.network.AppNetworkClient
import com.miraeldev.network.impl.AuthNetworkClientImpl
import me.tatarka.inject.annotations.Provides


interface NetworkSubComponent {
    @Provides
    @Singleton
    fun AppNetworkClientImpl.bind() : AppNetworkClient = this
    @Provides
    @Singleton
    fun AuthNetworkClientImpl.bind() : AuthNetworkClient = this
}