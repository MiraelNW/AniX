package com.miraeldev.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miraeldev.local.dao.FavouriteAnimeDao
import com.miraeldev.local.dao.SearchHistoryAnimeDao
import com.miraeldev.local.dao.UserDao
import com.miraeldev.local.dao.filmCategory.FilmCategoryDao
import com.miraeldev.local.dao.filmCategory.FilmCategoryPagingDao
import com.miraeldev.local.dao.filmCategory.FilmCategoryRemoteKeysDao
import com.miraeldev.local.dao.initialSearch.InitialSearchPagingDao
import com.miraeldev.local.dao.initialSearch.InitialSearchRemoteKeysDao
import com.miraeldev.local.dao.nameCategory.NameCategoryDao
import com.miraeldev.local.dao.nameCategory.NameCategoryPagingDao
import com.miraeldev.local.dao.nameCategory.NameCategoryRemoteKeysDao
import com.miraeldev.local.dao.newCategory.NewCategoryDao
import com.miraeldev.local.dao.newCategory.NewCategoryPagingDao
import com.miraeldev.local.dao.newCategory.NewCategoryRemoteKeysDao
import com.miraeldev.local.dao.popularCategory.PopularCategoryDao
import com.miraeldev.local.dao.popularCategory.PopularCategoryPagingDao
import com.miraeldev.local.dao.popularCategory.PopularCategoryRemoteKeysDao
import com.miraeldev.local.models.SearchHistoryDbModel
import com.miraeldev.local.models.favourite.AnimeInfoDbModel
import com.miraeldev.local.models.filmCategory.FilmCategoryAnimeInfoDbModel
import com.miraeldev.local.models.filmCategory.FilmCategoryRemoteKeys
import com.miraeldev.local.models.filmCategory.PagingFilmCategoryAnimeInfoDbModel
import com.miraeldev.local.models.initialSearch.InitialSearchRemoteKeys
import com.miraeldev.local.models.initialSearch.PagingInitialSearchAnimeInfoDbModel
import com.miraeldev.local.models.nameCategory.NameCategoryAnimeInfoDbModel
import com.miraeldev.local.models.nameCategory.NameCategoryRemoteKeys
import com.miraeldev.local.models.nameCategory.PagingNameCategoryAnimeInfoDbModel
import com.miraeldev.local.models.newCategory.NewCategoryAnimeInfoDbModel
import com.miraeldev.local.models.newCategory.NewCategoryRemoteKeys
import com.miraeldev.local.models.newCategory.PagingNewCategoryAnimeInfoDbModel
import com.miraeldev.local.models.popularCategory.PagingPopularCategoryAnimeInfoDbModel
import com.miraeldev.local.models.popularCategory.PopularCategoryAnimeInfoDbModel
import com.miraeldev.local.models.popularCategory.PopularCategoryRemoteKeys
import com.miraeldev.local.models.user.UserDbModel

@Database(
    entities = [
        AnimeInfoDbModel::class,
        SearchHistoryDbModel::class,

        NewCategoryAnimeInfoDbModel::class,
        NewCategoryRemoteKeys::class,
        PagingNewCategoryAnimeInfoDbModel::class,

        PopularCategoryAnimeInfoDbModel::class,
        PagingPopularCategoryAnimeInfoDbModel::class,
        PopularCategoryRemoteKeys::class,

        FilmCategoryAnimeInfoDbModel::class,
        PagingFilmCategoryAnimeInfoDbModel::class,
        FilmCategoryRemoteKeys::class,

        NameCategoryAnimeInfoDbModel::class,
        PagingNameCategoryAnimeInfoDbModel::class,
        NameCategoryRemoteKeys::class,

        PagingInitialSearchAnimeInfoDbModel::class,
        InitialSearchRemoteKeys::class,
        UserDbModel::class,
    ],
    version = 14,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun favouriteAnimeDao(): FavouriteAnimeDao

    abstract fun searchAnimeDao(): SearchHistoryAnimeDao

    abstract fun newCategoryDao(): NewCategoryDao
    abstract fun newCategoryPagingDao(): NewCategoryPagingDao
    abstract fun newCategoryRemoteKeys(): NewCategoryRemoteKeysDao

    abstract fun popularCategoryDao(): PopularCategoryDao
    abstract fun popularCategoryPagingDao(): PopularCategoryPagingDao
    abstract fun popularCategoryRemoteKeysDao(): PopularCategoryRemoteKeysDao

    abstract fun filmCategoryDao(): FilmCategoryDao
    abstract fun filmCategoryPagingDao(): FilmCategoryPagingDao
    abstract fun filmCategoryRemoteKeysDao(): FilmCategoryRemoteKeysDao

    abstract fun nameCategoryDao(): NameCategoryDao
    abstract fun nameCategoryPagingDao(): NameCategoryPagingDao
    abstract fun nameCategoryRemoteKeys(): NameCategoryRemoteKeysDao

    abstract fun initialSearchPagingDao(): InitialSearchPagingDao
    abstract fun initialSearchRemoteKeysDao(): InitialSearchRemoteKeysDao

}