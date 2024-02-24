package com.miraeldev.di

import com.miraeldev.PreferenceDataStoreAPI
import com.miraeldev.data.dataStore.localUser.LocalUserStoreApi

interface PreferenceDataStoreSubComponent {

    val providePreferenceDataStoreAPI: PreferenceDataStoreAPI

    val provideLocalUserStoreApi: LocalUserStoreApi

}