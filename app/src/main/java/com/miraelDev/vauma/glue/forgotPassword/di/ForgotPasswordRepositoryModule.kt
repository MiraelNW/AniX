package com.miraelDev.vauma.glue.forgotPassword.di

import com.miraelDev.vauma.glue.forgotPassword.repositories.ForgotPasswordRepositoryImpl
import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ForgotPasswordRepositoryModule {

    @Binds
    fun bindForgotPasswordRepository(impl: ForgotPasswordRepositoryImpl): ForgotPasswordRepository

}