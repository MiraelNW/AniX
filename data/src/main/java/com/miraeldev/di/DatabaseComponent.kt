package com.miraeldev.di

import android.content.Context
import androidx.room.Room
import com.miraeldev.data.local.AppDatabase
import com.miraeldev.data.local.dao.FavouriteAnimeDao
import com.miraeldev.data.local.dao.SearchHistoryAnimeDao
import com.miraeldev.data.local.dao.filmCategory.FilmCategoryPagingDao
import com.miraeldev.data.local.dao.filmCategory.FilmCategoryRemoteKeysDao
import com.miraeldev.data.local.dao.initialSearch.InitialSearchPagingDao
import com.miraeldev.data.local.dao.initialSearch.InitialSearchRemoteKeysDao
import com.miraeldev.data.local.dao.nameCategory.NameCategoryPagingDao
import com.miraeldev.data.local.dao.nameCategory.NameCategoryRemoteKeysDao
import com.miraeldev.data.local.dao.newCategory.NewCategoryPagingDao
import com.miraeldev.data.local.dao.newCategory.NewCategoryRemoteKeysDao
import com.miraeldev.data.local.dao.popularCategory.PopularCategoryPagingDao
import com.miraeldev.data.local.dao.popularCategory.PopularCategoryRemoteKeysDao
import com.miraeldev.di.scope.Singleton
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@Singleton
abstract class DatabaseComponent(@get:Provides val context: Context) {


    @Provides
    @Singleton
    internal fun provideAppDatabase(): AppDatabase {

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
    internal fun provideSearchHistoryDao(database: AppDatabase): SearchHistoryAnimeDao =
        database.searchAnimeDao()

    @Provides
    @Singleton
    internal fun provideFavouriteAnimeDao(database: AppDatabase): FavouriteAnimeDao =
        database.favouriteAnimeDao()


    @Provides
    @Singleton
    internal fun provideNewCategoryDao(database: AppDatabase): NewCategoryPagingDao =
        database.newCategoryPagingDao()


    @Provides
    @Singleton
    internal fun provideNewCategoryRemoteKeysDao(database: AppDatabase): NewCategoryRemoteKeysDao =
        database.newCategoryRemoteKeys()


    @Provides
    @Singleton
    internal fun providePopularCategoryDao(database: AppDatabase): PopularCategoryPagingDao =
        database.popularCategoryPagingDao()


    @Provides
    @Singleton
    internal fun providePopularCategoryRemoteKeysDao(database: AppDatabase): PopularCategoryRemoteKeysDao =
        database.popularCategoryRemoteKeysDao()


    @Provides
    @Singleton
    internal fun provideFilmCategoryDao(database: AppDatabase): FilmCategoryPagingDao =
        database.filmCategoryPagingDao()


    @Provides
    @Singleton
    internal fun provideFilmCategoryRemoteKeysDao(database: AppDatabase): FilmCategoryRemoteKeysDao =
        database.filmCategoryRemoteKeysDao()


    @Provides
    @Singleton
    internal fun provideNameCategoryDao(database: AppDatabase): NameCategoryPagingDao =
        database.nameCategoryPagingDao()


    @Provides
    @Singleton
    internal fun provideNameCategoryRemoteKeysDao(database: AppDatabase): NameCategoryRemoteKeysDao =
        database.nameCategoryRemoteKeys()


    @Provides
    @Singleton
    internal fun provideInitialSearchDao(database: AppDatabase): InitialSearchPagingDao =
        database.initialSearchPagingDao()


    @Provides
    @Singleton
    internal fun provideInitialSearchRemoteKeysDao(database: AppDatabase): InitialSearchRemoteKeysDao =
        database.initialSearchRemoteKeysDao()


    companion object {
        private const val DB_NAME = "main.db"
    }

}