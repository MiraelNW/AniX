package com.miraeldev.di

import android.content.Context
import me.tatarka.inject.annotations.Provides

interface DataComponent :
    PreferenceDataStoreSubComponent,
    RepoSubComponent,
    NetworkSubComponent