package com.miraeldev.di

interface DataComponent :
    DatabaseComponent,
    PreferenceDataStoreSubComponent,
    NetworkSubComponent,
    RepoSubComponent