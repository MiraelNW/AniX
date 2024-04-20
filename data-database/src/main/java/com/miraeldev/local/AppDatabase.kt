package com.miraeldev.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miraeldev.local.dao.FavouriteAnimeDao
import com.miraeldev.local.dao.SearchHistoryAnimeDao
import com.miraeldev.local.dao.UserDao
import com.miraeldev.local.dao.filmCategory.api.FilmCategoryPagingDao
import com.miraeldev.local.dao.filmCategory.api.FilmCategoryRemoteKeysDao
import com.miraeldev.local.dao.nameCategory.api.NameCategoryPagingDao
import com.miraeldev.local.dao.nameCategory.api.NameCategoryRemoteKeysDao
import com.miraeldev.local.dao.newCategory.api.NewCategoryPagingDao
import com.miraeldev.local.dao.newCategory.api.NewCategoryRemoteKeysDao
import com.miraeldev.local.dao.popularCategory.api.PopularCategoryPagingDao
import com.miraeldev.local.dao.popularCategory.api.PopularCategoryRemoteKeysDao
import com.miraeldev.local.models.SearchHistoryDbModel
import com.miraeldev.local.models.favourite.AnimeInfoDbModel
import com.miraeldev.local.models.filmCategory.FilmCategoryRemoteKeys
import com.miraeldev.local.models.filmCategory.PagingFilmCategoryAnimeInfoDbModel
import com.miraeldev.local.models.nameCategory.NameCategoryRemoteKeys
import com.miraeldev.local.models.nameCategory.PagingNameCategoryAnimeInfoDbModel
import com.miraeldev.local.models.newCategory.NewCategoryRemoteKeys
import com.miraeldev.local.models.newCategory.PagingNewCategoryAnimeInfoDbModel
import com.miraeldev.local.models.popularCategory.PagingPopularCategoryAnimeInfoDbModel
import com.miraeldev.local.models.popularCategory.PopularCategoryRemoteKeys
import com.miraeldev.local.models.user.UserDbModel

@Database(
    entities = [
        AnimeInfoDbModel::class,
        SearchHistoryDbModel::class,

        NewCategoryRemoteKeys::class,
        PagingNewCategoryAnimeInfoDbModel::class,

        PagingPopularCategoryAnimeInfoDbModel::class,
        PopularCategoryRemoteKeys::class,

        PagingFilmCategoryAnimeInfoDbModel::class,
        FilmCategoryRemoteKeys::class,

        PagingNameCategoryAnimeInfoDbModel::class,
        NameCategoryRemoteKeys::class,

        UserDbModel::class,
    ],
    version = 16,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun favouriteAnimeDao(): FavouriteAnimeDao

    abstract fun searchAnimeDao(): SearchHistoryAnimeDao

    abstract fun newCategoryPagingDao(): NewCategoryPagingDao
    abstract fun newCategoryRemoteKeys(): NewCategoryRemoteKeysDao

    abstract fun popularCategoryPagingDao(): PopularCategoryPagingDao
    abstract fun popularCategoryRemoteKeysDao(): PopularCategoryRemoteKeysDao

    abstract fun filmCategoryPagingDao(): FilmCategoryPagingDao
    abstract fun filmCategoryRemoteKeysDao(): FilmCategoryRemoteKeysDao

    abstract fun nameCategoryPagingDao(): NameCategoryPagingDao
    abstract fun nameCategoryRemoteKeys(): NameCategoryRemoteKeysDao
}