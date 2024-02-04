package com.miraelDev.vauma.glue.signIn.di

import com.miraelDev.vauma.glue.signIn.repositories.LoggerSignInImpl
import com.miraeldev.signin.data.repositories.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LoggerSignInModule {

    @Binds
    fun bindLogger(impl: LoggerSignInImpl): Logger

}