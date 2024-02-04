package com.miraelDev.vauma.glue.forgotPassword.di

import com.miraelDev.vauma.glue.account.repositories.AccountRepositoryImpl
import com.miraeldev.account.data.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AccountRepositoryModule {

    @Binds
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

}