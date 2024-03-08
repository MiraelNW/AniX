package com.miraeldev.dataStore.di

import com.miraeldev.dataStore.PreferenceClient
import com.miraeldev.dataStore.PreferenceClientImpl
import com.miraeldev.dataStore.userAuth.UserAuthRepository
import com.miraeldev.dataStore.userAuth.UserAuthRepositoryImpl
import com.miraeldev.dataStore.localUser.LocalUserManager
import com.miraeldev.dataStore.localUser.LocalUserStoreApi
import com.miraeldev.models.di.scope.Singleton
import me.tatarka.inject.annotations.Provides

interface PreferenceDataStoreSubComponent {

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