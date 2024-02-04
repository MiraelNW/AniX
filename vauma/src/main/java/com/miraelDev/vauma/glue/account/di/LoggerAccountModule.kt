package com.miraelDev.vauma.glue.account.di


import com.miraelDev.vauma.glue.account.repositories.LoggerAccountImpl
import com.miraelDev.vauma.glue.detailInfo.repositories.LoggerDetailInfoImpl
import com.miraeldev.account.data.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LoggerAccountModule {

    @Binds
    fun bindLogger(impl: LoggerAccountImpl): Logger

}