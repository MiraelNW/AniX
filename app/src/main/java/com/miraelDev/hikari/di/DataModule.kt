package com.miraelDev.hikari.di

import android.app.Application
import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.miraelDev.database.AppDatabase
import com.miraelDev.hikari.data.Repository.AnimeDetailRepositoryImpl
import com.miraelDev.hikari.data.Repository.AnimeListRepositoryImpl
import com.miraelDev.hikari.data.Repository.FavouriteAnimeRepositoryImpl
import com.miraelDev.hikari.data.Repository.FilterRepositoryImpl
import com.miraelDev.hikari.data.Repository.SearchAnimeRepositoryImpl
import com.miraelDev.hikari.data.Repository.VideoPlayerRepositoryImpl
import com.miraelDev.hikari.data.local.Dao.FavouriteAnimeDao
import com.miraelDev.hikari.data.local.Dao.FavouriteAnimeDaoImpl
import com.miraelDev.hikari.data.local.Dao.SearchAnimeDao
import com.miraelDev.hikari.data.local.Dao.SearchAnimeDaoImpl
import com.miraelDev.hikari.data.remote.NetworkHandler
import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import com.miraelDev.hikari.domain.repository.AnimeListRepository
import com.miraelDev.hikari.domain.repository.FavouriteAnimeRepository
import com.miraelDev.hikari.domain.repository.FilterAnimeRepository
import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import com.miraelDev.hikari.domain.repository.VideoPlayerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindAnimeListRepository(impl: AnimeListRepositoryImpl): AnimeListRepository

    @Binds
    @Singleton
    abstract fun bindSearchAnimeRepository(impl: SearchAnimeRepositoryImpl): SearchAnimeRepository

    @Binds
    @Singleton
    abstract fun bindFilterRepository(impl: FilterRepositoryImpl): FilterAnimeRepository

    @Binds
    @Singleton
    abstract fun bindAnimeDetailRepository(impl: AnimeDetailRepositoryImpl): AnimeDetailRepository


    @Binds
    @Singleton
    abstract fun bindVideoPlayerRepository(impl: VideoPlayerRepositoryImpl): VideoPlayerRepository

    @Binds
    @Singleton
    abstract fun bindFavouriteAnimeRepository(impl: FavouriteAnimeRepositoryImpl): FavouriteAnimeRepository


    @UnstableApi
    companion object {

        private const val PLAYER_SEEK_BACK_INCREMENT = 10 * 1000L
        private const val PLAYER_SEEK_FORWARD_INCREMENT = 10 * 1000L

        @Provides
        @Singleton
        fun provideVideoPlayer(application: Application): ExoPlayer {
            return ExoPlayer.Builder(application)
                    .apply {
                        setSeekBackIncrementMs(PLAYER_SEEK_BACK_INCREMENT)
                        setSeekForwardIncrementMs(PLAYER_SEEK_FORWARD_INCREMENT)
                    }
                    .build()
        }
    }

}