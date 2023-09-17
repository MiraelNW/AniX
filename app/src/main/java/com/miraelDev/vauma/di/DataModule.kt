package com.miraelDev.vauma.di

import android.app.Application
import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.miraelDev.vauma.data.repository.AnimeDetailRepositoryImpl
import com.miraelDev.vauma.data.repository.AnimeListRepositoryImpl
import com.miraelDev.vauma.data.repository.FavouriteAnimeRepositoryImpl
import com.miraelDev.vauma.data.repository.FilterRepositoryImpl
import com.miraelDev.vauma.data.repository.SearchAnimeRepositoryImpl
import com.miraelDev.vauma.data.repository.VideoPlayerRepositoryImpl
import com.miraelDev.vauma.data.dataStore.PreferenceManager
import com.miraelDev.vauma.data.downloadMananger.AndroidDownloader
import com.miraelDev.vauma.domain.downloader.Downloader
import com.miraelDev.vauma.domain.repository.AnimeDetailRepository
import com.miraelDev.vauma.domain.repository.AnimeListRepository
import com.miraelDev.vauma.domain.repository.FavouriteAnimeRepository
import com.miraelDev.vauma.domain.repository.FilterAnimeRepository
import com.miraelDev.vauma.domain.repository.SearchAnimeRepository
import com.miraelDev.vauma.domain.repository.VideoPlayerRepository
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

    @Binds
    @Singleton
    abstract fun bindDownloader(impl: AndroidDownloader): Downloader


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

        @Provides
        @Singleton
        fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager {
            return PreferenceManager(context)
        }

        @Provides
        @Singleton
        fun provideAndroidDownloader(@ApplicationContext context: Context): AndroidDownloader {
            return AndroidDownloader(context)
        }
    }

}