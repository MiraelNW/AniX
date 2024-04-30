package com.miraelDev.vauma.glue.account.di

import com.miraelDev.vauma.glue.account.repositories.AccountRepositoryImpl
import com.miraeldev.account.data.AccountRepository
import me.tatarka.inject.annotations.Provides

interface AccountFeatureComponent {

    @Provides
    fun AccountRepositoryImpl.bind(): AccountRepository = this
}