package com.miraeldev.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miraeldev.data.local.dao.FavouriteAnimeDao
import com.miraeldev.data.local.dao.SearchHistoryAnimeDao
import com.miraeldev.data.local.dao.filmCategory.FilmCategoryDao
import com.miraeldev.data.local.dao.filmCategory.FilmCategoryRemoteKeysDao
import com.miraeldev.data.local.dao.initialSearch.InitialSearchDao
import com.miraeldev.data.local.dao.initialSearch.InitialSearchRemoteKeysDao
import com.miraeldev.data.local.dao.nameCategory.NameCategoryDao
import com.miraeldev.data.local.dao.nameCategory.NameCategoryRemoteKeysDao
import com.miraeldev.data.local.dao.newCategory.NewCategoryDao
import com.miraeldev.data.local.dao.newCategory.NewCategoryRemoteKeysDao
import com.miraeldev.data.local.dao.popularCategory.PopularCategoryDao
import com.miraeldev.data.local.dao.popularCategory.PopularCategoryRemoteKeysDao
import com.miraeldev.data.local.models.SearchHistoryDbModel
import com.miraeldev.data.local.models.favourite.AnimeInfoDbModel
import com.miraeldev.data.local.models.filmCategory.FilmCategoryAnimeInfoDbModel
import com.miraeldev.data.local.models.filmCategory.FilmCategoryRemoteKeys
import com.miraeldev.data.local.models.initialSearch.InitialSearchAnimeInfoDbModel
import com.miraeldev.data.local.models.initialSearch.InitialSearchRemoteKeys
import com.miraeldev.data.local.models.nameCategory.NameCategoryAnimeInfoDbModel
import com.miraeldev.data.local.models.nameCategory.NameCategoryRemoteKeys
import com.miraeldev.data.local.models.newCategory.NewCategoryAnimeInfoDbModel
import com.miraeldev.data.local.models.newCategory.NewCategoryRemoteKeys
import com.miraeldev.data.local.models.popularCategory.PopularCategoryAnimeInfoDbModel
import com.miraeldev.data.local.models.popularCategory.PopularCategoryRemoteKeys

@Database(
    entities = [
        AnimeInfoDbModel::class,
        SearchHistoryDbModel::class,
        NewCategoryRemoteKeys::class,
        NewCategoryAnimeInfoDbModel::class,
        PopularCategoryAnimeInfoDbModel::class,
        PopularCategoryRemoteKeys::class,
        FilmCategoryRemoteKeys::class,
        FilmCategoryAnimeInfoDbModel::class,
        NameCategoryRemoteKeys::class,
        NameCategoryAnimeInfoDbModel::class,
        InitialSearchAnimeInfoDbModel::class,
        InitialSearchRemoteKeys::class
    ],
    version = 9,
    exportSchema = false
)
@TypeConverters(Converters::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteAnimeDao(): FavouriteAnimeDao

    abstract fun searchAnimeDao(): SearchHistoryAnimeDao

    abstract fun newCategoryDao(): NewCategoryDao
    abstract fun newCategoryRemoteKeys(): NewCategoryRemoteKeysDao

    abstract fun popularCategoryDao(): PopularCategoryDao
    abstract fun popularCategoryRemoteKeysDao(): PopularCategoryRemoteKeysDao

    abstract fun filmCategoryDao(): FilmCategoryDao
    abstract fun filmCategoryRemoteKeysDao(): FilmCategoryRemoteKeysDao

    abstract fun nameCategoryDao(): NameCategoryDao
    abstract fun nameCategoryRemoteKeys(): NameCategoryRemoteKeysDao

    abstract fun initialSearchDao(): InitialSearchDao
    abstract fun initialSearchRemoteKeysDao(): InitialSearchRemoteKeysDao

}