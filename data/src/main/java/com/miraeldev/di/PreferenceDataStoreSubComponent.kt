package com.miraeldev.di

import com.miraeldev.PreferenceDataStoreAPI
import com.miraeldev.data.dataStore.localUser.LocalUserManager
import com.miraeldev.data.dataStore.localUser.LocalUserStoreApi
import com.miraeldev.data.dataStore.preference.PreferenceManager
import me.tatarka.inject.annotations.Provides

interface PreferenceDataStoreSubComponent {

    @Provides
    fun PreferenceManager.bind(): PreferenceDataStoreAPI = this

    @Provides
    fun LocalUserManager.bind(): LocalUserStoreApi = this

}