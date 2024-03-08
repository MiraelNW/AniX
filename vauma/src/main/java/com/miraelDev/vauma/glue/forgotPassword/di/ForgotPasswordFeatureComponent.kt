package com.miraelDev.vauma.glue.forgotPassword.di

import com.miraelDev.vauma.glue.forgotPassword.repositories.ForgotPasswordRepositoryImpl
import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import me.tatarka.inject.annotations.Provides

interface ForgotPasswordFeatureComponent {

    @Provides
    fun ForgotPasswordRepositoryImpl.bind(): ForgotPasswordRepository = this
}