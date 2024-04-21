package com.miraeldev.impl.di

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.miraeldev.Database
import com.miraeldev.api.FavouriteAnimeDao
import com.miraeldev.api.SearchHistoryAnimeDao
import com.miraeldev.api.UserDao
import com.miraeldev.api.filmCategory.FilmCategoryDao
import com.miraeldev.api.filmCategory.FilmCategoryPagingDao
import com.miraeldev.api.initialSearch.InitialSearchPagingDao
import com.miraeldev.api.nameCategory.NameCategoryDao
import com.miraeldev.api.nameCategory.NameCategoryPagingDao
import com.miraeldev.api.newCategory.NewCategoryDao
import com.miraeldev.api.newCategory.NewCategoryPagingDao
import com.miraeldev.api.popularCategory.PopularCategoryDao
import com.miraeldev.api.popularCategory.PopularCategoryPagingDao
import com.miraeldev.impl.dao.FavouriteAnimeDaoImpl
import com.miraeldev.impl.dao.SearchHistoryAnimeDaoImpl
import com.miraeldev.impl.dao.UserDaoImpl
import com.miraeldev.impl.dao.filmCategory.FilmCategoryDaoImpl
import com.miraeldev.impl.dao.filmCategory.FilmCategoryPagingDaoImpl
import com.miraeldev.impl.dao.initialSearch.InitialSearchPagingDaoImpl
import com.miraeldev.impl.dao.nameCategory.NameCategoryDaoImpl
import com.miraeldev.impl.dao.nameCategory.NameCategoryPagingDaoImpl
import com.miraeldev.impl.dao.newCategory.NewCategoryDaoImpl
import com.miraeldev.impl.dao.newCategory.NewCategoryPagingDaoImpl
import com.miraeldev.impl.dao.popularCategory.PopularCategoryDaoImpl
import com.miraeldev.impl.dao.popularCategory.PopularCategoryPagingDaoImpl
import com.miraeldev.impl.genresAdapter
import com.miraeldev.impl.imageAdapter
import com.miraeldev.impl.lastWatchedAnimeAdapter
import com.miraeldev.impl.videoUrlsAdapter
import com.miraeldev.models.di.scope.Singleton
import me.tatarka.inject.annotations.Provides
import tables.FavouriteAnimeInfoDbModel
import tables.UserDbModel
import tables.filmcategory.FilmCategoryAnimeInfoDbModel
import tables.filmcategory.FilmCategoryPagingInfoDbModel
import tables.initialsearch.InitialSearchPagingInfoDbModel
import tables.namecategory.NameCategoryAnimeInfoDbModel
import tables.namecategory.NameCategoryPagingInfoDbModel
import tables.newcategory.NewCategoryAnimeInfoDbModel
import tables.newcategory.NewCategoryPagingInfoDbModel
import tables.popularcategory.PopularCategoryAnimeInfoDbModel
import tables.popularcategory.PopularCategoryPagingInfoDbModel

interface DatabaseComponent {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Database(
            driver = AndroidSqliteDriver(Database.Schema, context, "Database"),
            FavouriteAnimeInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            FilmCategoryAnimeInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            FilmCategoryPagingInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            InitialSearchPagingInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            NameCategoryAnimeInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            NameCategoryPagingInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            NewCategoryAnimeInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            NewCategoryPagingInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            PopularCategoryAnimeInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            PopularCategoryPagingInfoDbModel.Adapter(imageAdapter, videoUrlsAdapter, genresAdapter),
            UserDbModel.Adapter(lastWatchedAnimeAdapter),
        )
    }

    @Provides
    @Singleton
    fun UserDaoImpl.bind(): UserDao = this

    @Provides
    @Singleton
    fun SearchHistoryAnimeDaoImpl.bind(): SearchHistoryAnimeDao = this

    @Provides
    @Singleton
    fun FavouriteAnimeDaoImpl.bind(): FavouriteAnimeDao = this

    @Provides
    @Singleton
    fun NewCategoryPagingDaoImpl.bind(): NewCategoryPagingDao = this

    @Provides
    @Singleton
    fun PopularCategoryPagingDaoImpl.bind(): PopularCategoryPagingDao = this

    @Provides
    @Singleton
    fun NameCategoryPagingDaoImpl.bind(): NameCategoryPagingDao = this

    @Provides
    @Singleton
    fun FilmCategoryPagingDaoImpl.bind(): FilmCategoryPagingDao = this

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