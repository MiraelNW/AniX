package com.miraelDev.vauma.glue.account.di


import com.miraelDev.vauma.glue.account.repositories.LoggerAccountImpl
import com.miraeldev.account.data.Logger
import me.tatarka.inject.annotations.Provides

interface LoggerAccountModule {

    @Provides
    fun LoggerAccountImpl.bind(): Logger = this
}