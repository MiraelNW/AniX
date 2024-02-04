package com.miraelDev.vauma.glue.signIn.di

import com.miraelDev.vauma.glue.signIn.repositories.SignInRepositoryImpl
import com.miraeldev.signin.data.repositories.SignInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SignInRepositoryModule {

    @Binds
    fun bindSignInRepository(impl: SignInRepositoryImpl): SignInRepository
}