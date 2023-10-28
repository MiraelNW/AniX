package com.miraelDev.vauma.glue.signUp.di

import com.miraelDev.vauma.glue.signUp.repositories.SignUpRepositoryImpl
import com.miraeldev.signup.data.SignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SignUpRepositoryModule {

    @Binds
    fun bindSignUpRepository(impl: SignUpRepositoryImpl): SignUpRepository

}