package com.miraelDev.vauma.di

import android.content.Context
import androidx.room.Room
import com.miraelDev.vauma.data.local.AppDatabase
import com.miraelDev.vauma.data.local.dao.FavouriteAnimeDao
import com.miraelDev.vauma.data.local.dao.SearchHistoryAnimeDao
import com.miraelDev.vauma.data.local.dao.newCategory.FilmCategoryDao
import com.miraelDev.vauma.data.local.dao.newCategory.FilmCategoryRemoteKeysDao
import com.miraelDev.vauma.data.local.dao.initialSearch.InitialSearchDao
import com.miraelDev.vauma.data.local.dao.initialSearch.InitialSearchRemoteKeysDao
import com.miraelDev.vauma.data.local.dao.newCategory.NameCategoryDao
import com.miraelDev.vauma.data.local.dao.newCategory.NameCategoryRemoteKeysDao
import com.miraelDev.vauma.data.local.dao.newCategory.NewCategoryDao
import com.miraelDev.vauma.data.local.dao.newCategory.NewCategoryRemoteKeysDao
import com.miraelDev.vauma.data.local.dao.popularCategory.PopularCategoryDao
import com.miraelDev.vauma.data.local.dao.popularCategory.PopularCategoryRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

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
    fun provideNewCategoryDao(database: AppDatabase): NewCategoryDao {
        return database.newCategoryDao()
    }

    @Provides
    @Singleton
    fun provideNewCategoryRemoteKeysDao(database: AppDatabase): NewCategoryRemoteKeysDao {
        return database.newCategoryRemoteKeys()
    }

    @Provides
    @Singleton
    fun providePopularCategoryDao(database: AppDatabase): PopularCategoryDao {
        return database.popularCategoryDao()
    }

    @Provides
    @Singleton
    fun providePopularCategoryRemoteKeysDao(database: AppDatabase): PopularCategoryRemoteKeysDao {
        return database.popularCategoryRemoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideFilmCategoryDao(database: AppDatabase): FilmCategoryDao {
        return database.filmCategoryDao()
    }

    @Provides
    @Singleton
    fun provideFilmCategoryRemoteKeysDao(database: AppDatabase): FilmCategoryRemoteKeysDao {
        return database.filmCategoryRemoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideNameCategoryDao(database: AppDatabase): NameCategoryDao {
        return database.nameCategoryDao()
    }

    @Provides
    @Singleton
    fun provideNameCategoryRemoteKeysDao(database: AppDatabase): NameCategoryRemoteKeysDao {
        return database.nameCategoryRemoteKeys()
    }

    @Provides
    @Singleton
    fun provideInitialSearchDao(database: AppDatabase): InitialSearchDao {
        return database.initialSearchDao()
    }

    @Provides
    @Singleton
    fun provideInitialSearchRemoteKeysDao(database: AppDatabase): InitialSearchRemoteKeysDao {
        return database.initialSearchRemoteKeysDao()
    }

}