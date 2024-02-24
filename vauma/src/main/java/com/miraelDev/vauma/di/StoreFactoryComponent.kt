package com.miraelDev.vauma.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

abstract class StoreFactoryComponent {

    @Provides
    fun provideStoreFactory(): StoreFactory = DefaultStoreFactory()

}