package com.miraeldev.impl.di

import com.miraeldev.models.di.scope.Singleton
import com.miraeldev.impl.AppNetworkClientImpl
import com.miraeldev.api.AuthNetworkClient
import com.miraeldev.api.AppNetworkClient
import com.miraeldev.impl.AuthNetworkClientImpl
import me.tatarka.inject.annotations.Provides


interface NetworkComponent {
    @Provides
    @Singleton
    fun AppNetworkClientImpl.bind() : AppNetworkClient = this
    @Provides
    @Singleton
    fun AuthNetworkClientImpl.bind() : AuthNetworkClient = this
}