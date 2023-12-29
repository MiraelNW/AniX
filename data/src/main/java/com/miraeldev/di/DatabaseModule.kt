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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

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
    fun provideNewCategoryDao(database: AppDatabase): NewCategoryPagingDao {
        return database.newCategoryPagingDao()
    }

    @Provides
    @Singleton
    fun provideNewCategoryRemoteKeysDao(database: AppDatabase): NewCategoryRemoteKeysDao {
        return database.newCategoryRemoteKeys()
    }

    @Provides
    @Singleton
    fun providePopularCategoryDao(database: AppDatabase): PopularCategoryPagingDao {
        return database.popularCategoryPagingDao()
    }

    @Provides
    @Singleton
    fun providePopularCategoryRemoteKeysDao(database: AppDatabase): PopularCategoryRemoteKeysDao {
        return database.popularCategoryRemoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideFilmCategoryDao(database: AppDatabase): FilmCategoryPagingDao {
        return database.filmCategoryPagingDao()
    }

    @Provides
    @Singleton
    fun provideFilmCategoryRemoteKeysDao(database: AppDatabase): FilmCategoryRemoteKeysDao {
        return database.filmCategoryRemoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideNameCategoryDao(database: AppDatabase): NameCategoryPagingDao {
        return database.nameCategoryPagingDao()
    }

    @Provides
    @Singleton
    fun provideNameCategoryRemoteKeysDao(database: AppDatabase): NameCategoryRemoteKeysDao {
        return database.nameCategoryRemoteKeys()
    }

    @Provides
    @Singleton
    fun provideInitialSearchDao(database: AppDatabase): InitialSearchPagingDao {
        return database.initialSearchDao()
    }

    @Provides
    @Singleton
    fun provideInitialSearchRemoteKeysDao(database: AppDatabase): InitialSearchRemoteKeysDao {
        return database.initialSearchRemoteKeysDao()
    }

}