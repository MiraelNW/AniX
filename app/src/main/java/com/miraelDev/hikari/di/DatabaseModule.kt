package com.miraelDev.hikari.di

import android.content.Context
import androidx.room.Room
import com.miraelDev.hikari.data.local.AppDatabase
import com.miraelDev.hikari.data.local.Dao.FavouriteAnimeDao
import com.miraelDev.hikari.data.local.Dao.SearchHistoryAnimeDao
import com.miraelDev.hikari.data.local.Dao.newCategory.FilmCategoryDao
import com.miraelDev.hikari.data.local.Dao.newCategory.FilmCategoryRemoteKeysDao
import com.miraelDev.hikari.data.local.Dao.newCategory.NameCategoryDao
import com.miraelDev.hikari.data.local.Dao.newCategory.NameCategoryRemoteKeysDao
import com.miraelDev.hikari.data.local.Dao.newCategory.NewCategoryDao
import com.miraelDev.hikari.data.local.Dao.newCategory.NewCategoryRemoteKeysDao
import com.miraelDev.hikari.data.local.Dao.newCategory.PopularCategoryDao
import com.miraelDev.hikari.data.local.Dao.newCategory.PopularCategoryRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "main.db"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {

        var db: AppDatabase? = null
        val LOCK = Any()
        synchronized(LOCK) {
            db?.let { return it }
            val instance =
                Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            db = instance
            return instance
        }

    }


    @Provides
    @Singleton
    fun provideSearchHistoryDao(database: AppDatabase): SearchHistoryAnimeDao {
        return database.searchAnimeDao()
    }

    @Provides
    @Singleton
    fun provideFavouriteAnimeDao(database: AppDatabase): FavouriteAnimeDao {
        return database.favouriteAnimeDao()
    }

    @Provides
    @Singleton
    fun provideNewCategoryDao(database: AppDatabase): NewCategoryDao {
        return database.newCategoryDao()
    }

    @Provides
    @Singleton
    fun provideNewCategoryRemoteKeysDao(database: AppDatabase): NewCategoryRemoteKeysDao {
        return database.newCategoryRemoteKeys()
    }

    @Provides
    @Singleton
    fun providePopularCategoryDao(database: AppDatabase): PopularCategoryDao {
        return database.popularCategoryDao()
    }

    @Provides
    @Singleton
    fun providePopularCategoryRemoteKeysDao(database: AppDatabase): PopularCategoryRemoteKeysDao {
        return database.popularCategoryRemoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideFilmCategoryDao(database: AppDatabase): FilmCategoryDao {
        return database.filmCategoryDao()
    }

    @Provides
    @Singleton
    fun provideFilmCategoryRemoteKeysDao(database: AppDatabase): FilmCategoryRemoteKeysDao {
        return database.filmCategoryRemoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideNameCategoryDao(database: AppDatabase): NameCategoryDao {
        return database.nameCategoryDao()
    }

    @Provides
    @Singleton
    fun provideNameCategoryRemoteKeysDao(database: AppDatabase): NameCategoryRemoteKeysDao {
        return database.nameCategoryRemoteKeys()
    }

}