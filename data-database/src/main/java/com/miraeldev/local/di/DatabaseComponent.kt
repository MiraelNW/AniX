package com.miraeldev.local.di

import android.content.Context
import androidx.room.Room
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.miraeldev.Database
import com.miraeldev.local.AppDatabase
import com.miraeldev.local.dao.FavouriteAnimeDao
import com.miraeldev.local.dao.SearchHistoryAnimeDao
import com.miraeldev.local.dao.filmCategory.api.FilmCategoryDao
import com.miraeldev.local.dao.filmCategory.api.FilmCategoryPagingDao
import com.miraeldev.local.dao.filmCategory.api.FilmCategoryRemoteKeysDao
import com.miraeldev.local.dao.filmCategory.impl.FilmCategoryDaoImpl
import com.miraeldev.local.dao.initialSearch.api.InitialSearchPagingDao
import com.miraeldev.local.dao.initialSearch.impl.InitialSearchPagingDaoImpl
import com.miraeldev.local.dao.nameCategory.api.NameCategoryDao
import com.miraeldev.local.dao.nameCategory.api.NameCategoryPagingDao
import com.miraeldev.local.dao.nameCategory.api.NameCategoryRemoteKeysDao
import com.miraeldev.local.dao.nameCategory.impl.NameCategoryDaoImpl
import com.miraeldev.local.dao.newCategory.api.NewCategoryDao
import com.miraeldev.local.dao.newCategory.api.NewCategoryPagingDao
import com.miraeldev.local.dao.newCategory.api.NewCategoryRemoteKeysDao
import com.miraeldev.local.dao.newCategory.impl.NewCategoryDaoImpl
import com.miraeldev.local.dao.popularCategory.api.PopularCategoryDao
import com.miraeldev.local.dao.popularCategory.api.PopularCategoryPagingDao
import com.miraeldev.local.dao.popularCategory.api.PopularCategoryRemoteKeysDao
import com.miraeldev.local.dao.popularCategory.impl.PopularCategoryDaoImpl
import com.miraeldev.local.genresAdapter
import com.miraeldev.local.imageAdapter
import com.miraeldev.local.videoUrlsAdapter
import com.miraeldev.models.di.scope.Singleton
import me.tatarka.inject.annotations.Provides
import tables.filmcategory.FilmCategoryAnimeInfoDbModel
import tables.initialsearch.InitialSearchPagingInfoDbModel
import tables.namecategory.NameCategoryAnimeInfoDbModel
import tables.newcategory.NewCategoryAnimeInfoDbModel
import tables.popularcategory.PopularCategoryAnimeInfoDbModel

interface DatabaseComponent {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Database(
            driver = AndroidSqliteDriver(Database.Schema, context, "Database"),
            FilmCategoryAnimeInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            InitialSearchPagingInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            NameCategoryAnimeInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            NewCategoryAnimeInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            PopularCategoryAnimeInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
        )
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
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
    fun FilmCategoryDaoImpl.bind(): FilmCategoryDao = this

    @Provides
    @Singleton
    fun NameCategoryDaoImpl.bind(): NameCategoryDao = this

    @Provides
    @Singleton
    fun NewCategoryDaoImpl.bind(): NewCategoryDao = this

    @Provides
    @Singleton
    fun PopularCategoryDaoImpl.bind(): PopularCategoryDao = this

    @Provides
    @Singleton
    fun InitialSearchPagingDaoImpl.bind(): InitialSearchPagingDao = this

    companion object {
        private const val DB_NAME = "main.db"
    }
}