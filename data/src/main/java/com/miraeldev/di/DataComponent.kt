package com.miraeldev.di

import com.miraeldev.dataStore.di.PreferenceDataStoreSubComponent
import com.miraeldev.local.di.DatabaseComponent
import com.miraeldev.network.di.NetworkSubComponent

interface DataComponent :
    DatabaseComponent,
    PreferenceDataStoreSubComponent,
    NetworkSubComponent,
    RepoSubComponent