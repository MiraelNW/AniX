package com.miraelDev.hikari.di

import android.app.Application
import com.miraelDev.hikari.data.Repository.AnimeDetailRepositoryImpl
import com.miraelDev.hikari.data.Repository.AnimeListRepositoryImpl
import com.miraelDev.hikari.data.Repository.FilterRepositoryImpl
import com.miraelDev.hikari.data.Repository.SearchAnimeRepositoryImpl
import com.miraelDev.hikari.data.local.AppDataBase
import com.miraelDev.hikari.data.local.Dao.FavouriteAnimeDao
import com.miraelDev.hikari.data.local.Dao.SearchAnimeDao
import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import com.miraelDev.hikari.domain.repository.AnimeListRepository
import com.miraelDev.hikari.domain.repository.FilterAnimeRepository
import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindAnimeListRepository(impl: AnimeListRepositoryImpl): AnimeListRepository

    @Binds
    @Singleton
    abstract fun bindSearchAnimeRepository(impl: SearchAnimeRepositoryImpl): SearchAnimeRepository

    @Binds
    @Singleton
    abstract fun bindFilterRepository(impl: FilterRepositoryImpl): FilterAnimeRepository
    @Binds
    @Singleton
    abstract fun bindAnimeDetailRepository(impl: AnimeDetailRepositoryImpl): AnimeDetailRepository


    companion object {
        @Provides
        fun provideSearchAnimeDao(application: Application): SearchAnimeDao {
            return AppDataBase.getInstance(application).searchAnimeDao()
        }

        @Provides
        fun provideFavouriteAnimeDao(application: Application): FavouriteAnimeDao {
            return AppDataBase.getInstance(application).favouriteAnimeDao()
        }
    }

}