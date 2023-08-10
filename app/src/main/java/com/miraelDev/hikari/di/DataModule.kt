package com.miraelDev.hikari.di

import android.app.Application
import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.miraelDev.database.AppDatabase
import com.miraelDev.hikari.data.Repository.AnimeDetailRepositoryImpl
import com.miraelDev.hikari.data.Repository.AnimeListRepositoryImpl
import com.miraelDev.hikari.data.Repository.FilterRepositoryImpl
import com.miraelDev.hikari.data.Repository.SearchAnimeRepositoryImpl
import com.miraelDev.hikari.data.local.Dao.SearchAnimeDao
import com.miraelDev.hikari.data.local.Dao.SearchAnimeDaoImpl
import com.miraelDev.hikari.data.remote.NetworkHandler
import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import com.miraelDev.hikari.domain.repository.AnimeListRepository
import com.miraelDev.hikari.domain.repository.FilterAnimeRepository
import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Binds
    @Singleton
    abstract fun bindSearchAnimeDao(impl: SearchAnimeDaoImpl): SearchAnimeDao


    companion object {
        @Provides
        @Singleton
        fun provideSqlDriver(app: Application): SqlDriver {
            return AndroidSqliteDriver(
                    schema = AppDatabase.Schema,
                    context = app,
                    name = "app_database"
            )
        }

        @Provides
        @Singleton
        fun provideDatabase(driver: SqlDriver): AppDatabase {
            return AppDatabase(driver)
        }

//        @Provides
//        @Singleton
//        fun provideNetworkHandler(@ApplicationContext context: Context): NetworkHandler {
//            return NetworkHandler(context)
//        }

    }

}