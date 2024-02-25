package com.miraelDev.vauma.glue.signUp.di

import com.miraelDev.vauma.glue.signUp.repositories.SignUpRepositoryImpl
import com.miraeldev.signup.data.SignUpRepository
import me.tatarka.inject.annotations.Provides


interface SignUpFeatureComponent {
    @Provides
    fun SignUpRepositoryImpl.bind(): SignUpRepository = this
}