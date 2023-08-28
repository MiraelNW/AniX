package com.miraelDev.hikari.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
import com.miraelDev.hikari.data.local.models.AnimeInfoDbModel
import com.miraelDev.hikari.data.local.models.SearchHistoryDbModel
import com.miraelDev.hikari.data.local.models.newCategory.FilmCategoryAnimeInfoDbModel
import com.miraelDev.hikari.data.local.models.newCategory.FilmCategoryRemoteKeys
import com.miraelDev.hikari.data.local.models.newCategory.NameCategoryAnimeInfoDbModel
import com.miraelDev.hikari.data.local.models.newCategory.NameCategoryRemoteKeys
import com.miraelDev.hikari.data.local.models.newCategory.NewCategoryAnimeInfoDbModel
import com.miraelDev.hikari.data.local.models.newCategory.NewCategoryRemoteKeys
import com.miraelDev.hikari.data.local.models.newCategory.PopularCategoryAnimeInfoDbModel
import com.miraelDev.hikari.data.local.models.newCategory.PopularCategoryRemoteKeys

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
        NameCategoryAnimeInfoDbModel::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
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

}