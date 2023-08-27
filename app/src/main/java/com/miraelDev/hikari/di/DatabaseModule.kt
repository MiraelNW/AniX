package com.miraelDev.hikari.di

import android.content.Context
import com.miraelDev.hikari.data.local.AppDatabase
import com.miraelDev.hikari.data.local.Dao.FavouriteAnimeDao
import com.miraelDev.hikari.data.local.Dao.SearchHistoryAnimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSearchHistoryDao(@ApplicationContext context: Context): SearchHistoryAnimeDao {
        return AppDatabase.getInstance(context).searchAnimeDao()
    }

    @Provides
    @Singleton
    fun provideFavouriteAnimeDao(@ApplicationContext context: Context): FavouriteAnimeDao {
        return AppDatabase.getInstance(context).favouriteAnimeDao()
    }

}