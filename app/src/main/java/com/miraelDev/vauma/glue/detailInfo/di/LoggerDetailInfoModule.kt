package com.miraelDev.vauma.glue.detailInfo.di


import com.miraelDev.vauma.glue.detailInfo.repositories.LoggerDetailInfoImpl
import com.miraeldev.detailinfo.data.repositories.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LoggerDetailInfoModule {

    @Binds
    fun bindLogger(impl: LoggerDetailInfoImpl): Logger

}