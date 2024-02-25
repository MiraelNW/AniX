package com.miraelDev.vauma.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import me.tatarka.inject.annotations.Provides


interface StoreFactoryComponent {
    @Provides
    fun provideStoreFactory(): StoreFactory = DefaultStoreFactory()
}