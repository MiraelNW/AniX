package com.miraeldev.impl.di

import com.miraeldev.di.RepoSubComponent
import com.miraeldev.local.di.DatabaseComponent

interface DataComponent :
    DatabaseComponent,
    RepoSubComponent