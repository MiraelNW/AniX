package com.miraelDev.hikari.di

import com.miraelDev.hikari.data.local.Dao.FavouriteAnimeDao
import com.miraelDev.hikari.data.local.Dao.FavouriteAnimeDaoImpl
import com.miraelDev.hikari.data.local.Dao.SearchAnimeDao
import com.miraelDev.hikari.data.local.Dao.SearchAnimeDaoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DaoModule {

    @Binds
    @Singleton
    abstract fun bindSearchAnimeDao(impl: SearchAnimeDaoImpl): SearchAnimeDao

    @Binds
    @Singleton
    abstract fun bindFavouriteAnimeDao(impl: FavouriteAnimeDaoImpl): FavouriteAnimeDao
}