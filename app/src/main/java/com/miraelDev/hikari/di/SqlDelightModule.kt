package com.miraelDev.hikari.di

import android.app.Application
import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.adapter.primitive.FloatColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.miraelDev.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hikari.database.FavouriteAnimeEntity
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SqlDelightModule {

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
        return AppDatabase(
                driver = driver,
                FavouriteAnimeEntityAdapter = FavouriteAnimeEntity.Adapter(
                        genresAdapter = listOfStringsAdapter,
                        durationAdapter = IntColumnAdapter,
                        episodesAdapter = IntColumnAdapter,
                        scoreAdapter = FloatColumnAdapter
                )
        )
    }

    private val listOfStringsAdapter = object : ColumnAdapter<List<String>, String> {
        override fun decode(databaseValue: String) = databaseValue.split(",")
        override fun encode(value: List<String>) = value.joinToString(separator = ",")
    }
}