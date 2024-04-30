package com.miraeldev.impl.di

import com.miraeldev.api.LocalUserStoreApi
import com.miraeldev.api.PreferenceClient
import com.miraeldev.api.UserAuthRepository
import com.miraeldev.impl.PreferenceClientImpl
import com.miraeldev.impl.localUser.LocalUserManager
import com.miraeldev.impl.userAuth.UserAuthRepositoryImpl
import com.miraeldev.models.di.scope.Singleton
import me.tatarka.inject.annotations.Provides

interface PreferenceDataStoreComponent {

    @Provides
    @Singleton
    fun PreferenceClientImpl.bind(): PreferenceClient = this
    @Provides
    @Singleton
    fun UserAuthRepositoryImpl.bind(): UserAuthRepository = this
    @Provides
    @Singleton
    fun LocalUserManager.bind(): LocalUserStoreApi = this
}