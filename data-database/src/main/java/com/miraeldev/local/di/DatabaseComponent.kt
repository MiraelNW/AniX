package com.miraeldev.local.di

import android.content.Context
import androidx.room.Room
import com.miraeldev.local.AppDatabase
import com.miraeldev.local.dao.FavouriteAnimeDao
import com.miraeldev.local.dao.SearchHistoryAnimeDao
import com.miraeldev.local.dao.filmCategory.FilmCategoryPagingDao
import com.miraeldev.local.dao.filmCategory.FilmCategoryRemoteKeysDao
import com.miraeldev.local.dao.initialSearch.InitialSearchPagingDao
import com.miraeldev.local.dao.initialSearch.InitialSearchRemoteKeysDao
import com.miraeldev.local.dao.nameCategory.NameCategoryPagingDao
import com.miraeldev.local.dao.nameCategory.NameCategoryRemoteKeysDao
import com.miraeldev.local.dao.newCategory.NewCategoryPagingDao
import com.miraeldev.local.dao.newCategory.NewCategoryRemoteKeysDao
import com.miraeldev.local.dao.popularCategory.PopularCategoryPagingDao
import com.miraeldev.local.dao.popularCategory.PopularCategoryRemoteKeysDao
import com.miraeldev.models.di.scope.Singleton
import me.tatarka.inject.annotations.Provides

interface DatabaseComponent {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
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
    fun provideSearchHistoryDao(database: AppDatabase): SearchHistoryAnimeDao =
        database.searchAnimeDao()

    @Provides
    @Singleton
    fun provideFavouriteAnimeDao(database: AppDatabase): FavouriteAnimeDao =
        database.favouriteAnimeDao()


    @Provides
    @Singleton
    fun provideNewCategoryDao(database: AppDatabase): NewCategoryPagingDao =
        database.newCategoryPagingDao()


    @Provides
    @Singleton
    fun provideNewCategoryRemoteKeysDao(database: AppDatabase): NewCategoryRemoteKeysDao =
        database.newCategoryRemoteKeys()


    @Provides
    @Singleton
    fun providePopularCategoryDao(database: AppDatabase): PopularCategoryPagingDao =
        database.popularCategoryPagingDao()


    @Provides
    @Singleton
    fun providePopularCategoryRemoteKeysDao(database: AppDatabase): PopularCategoryRemoteKeysDao =
        database.popularCategoryRemoteKeysDao()


    @Provides
    @Singleton
    fun provideFilmCategoryDao(database: AppDatabase): FilmCategoryPagingDao =
        database.filmCategoryPagingDao()


    @Provides
    @Singleton
    fun provideFilmCategoryRemoteKeysDao(database: AppDatabase): FilmCategoryRemoteKeysDao =
        database.filmCategoryRemoteKeysDao()


    @Provides
    @Singleton
    fun provideNameCategoryDao(database: AppDatabase): NameCategoryPagingDao =
        database.nameCategoryPagingDao()


    @Provides
    @Singleton
    fun provideNameCategoryRemoteKeysDao(database: AppDatabase): NameCategoryRemoteKeysDao =
        database.nameCategoryRemoteKeys()


    @Provides
    @Singleton
    fun provideInitialSearchDao(database: AppDatabase): InitialSearchPagingDao =
        database.initialSearchPagingDao()


    @Provides
    @Singleton
    fun provideInitialSearchRemoteKeysDao(database: AppDatabase): InitialSearchRemoteKeysDao =
        database.initialSearchRemoteKeysDao()

    companion object {
        private const val DB_NAME = "main.db"
    }
}