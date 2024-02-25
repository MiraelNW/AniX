package com.miraelDev.vauma.glue.signIn.di

import com.miraelDev.vauma.glue.signIn.repositories.SignInRepositoryImpl
import com.miraeldev.signin.data.repositories.SignInRepository
import me.tatarka.inject.annotations.Provides

interface SignInFeatureComponent {

    @Provides
    fun SignInRepositoryImpl.bind(): SignInRepository = this
}