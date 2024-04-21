package com.miraeldev.local.di

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.miraeldev.Database
import com.miraeldev.local.dao.favouriteAnime.FavouriteAnimeDao
import com.miraeldev.local.dao.searchHistoryDao.SearchHistoryAnimeDao
import com.miraeldev.local.dao.favouriteAnime.FavouriteAnimeDaoImpl
import com.miraeldev.local.dao.filmCategory.api.FilmCategoryDao
import com.miraeldev.local.dao.filmCategory.api.FilmCategoryPagingDao
import com.miraeldev.local.dao.filmCategory.impl.FilmCategoryDaoImpl
import com.miraeldev.local.dao.filmCategory.impl.FilmCategoryPagingDaoImpl
import com.miraeldev.local.dao.initialSearch.api.InitialSearchPagingDao
import com.miraeldev.local.dao.initialSearch.impl.InitialSearchPagingDaoImpl
import com.miraeldev.local.dao.nameCategory.api.NameCategoryDao
import com.miraeldev.local.dao.nameCategory.api.NameCategoryPagingDao
import com.miraeldev.local.dao.nameCategory.impl.NameCategoryDaoImpl
import com.miraeldev.local.dao.nameCategory.impl.NameCategoryPagingDaoImpl
import com.miraeldev.local.dao.newCategory.api.NewCategoryDao
import com.miraeldev.local.dao.newCategory.api.NewCategoryPagingDao
import com.miraeldev.local.dao.newCategory.impl.NewCategoryDaoImpl
import com.miraeldev.local.dao.newCategory.impl.NewCategoryPagingDaoImpl
import com.miraeldev.local.dao.popularCategory.api.PopularCategoryDao
import com.miraeldev.local.dao.popularCategory.api.PopularCategoryPagingDao
import com.miraeldev.local.dao.popularCategory.impl.PopularCategoryDaoImpl
import com.miraeldev.local.dao.popularCategory.impl.PopularCategoryPagingDaoImpl
import com.miraeldev.local.dao.searchHistoryDao.SearchHistoryAnimeDaoImpl
import com.miraeldev.local.dao.user.UserDao
import com.miraeldev.local.dao.user.UserDaoImpl
import com.miraeldev.local.genresAdapter
import com.miraeldev.local.imageAdapter
import com.miraeldev.local.lastWatchedAnimeAdapter
import com.miraeldev.local.videoUrlsAdapter
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