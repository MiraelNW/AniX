package com.miraeldev.di

import android.app.Application
import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.miraeldev.AnimeDetailDataRepository
import com.miraeldev.AnimeListDataRepository
import com.miraeldev.Downloader
import com.miraeldev.FavouriteAnimeDataRepository
import com.miraeldev.FilterAnimeDataRepository
import com.miraeldev.SearchAnimeDataRepository
import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.VideoPlayerDataRepository
import com.miraeldev.data.dataStore.localUser.LocalUserManager
import com.miraeldev.data.dataStore.localUser.LocalUserStoreApi
import com.miraeldev.PreferenceDataStoreAPI
import com.miraeldev.data.dataStore.preference.PreferenceManager
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.downloadMananger.AndroidDownloader
import com.miraeldev.data.remote.userApiService.UserApiService
import com.miraeldev.data.remote.userApiService.UserApiServiceImpl
import com.miraeldev.data.repository.AnimeDetailDataRepositoryImpl
import com.miraeldev.data.repository.AnimeListDataRepositoryImpl
import com.miraeldev.data.repository.FavouriteAnimeDataRepositoryImpl
import com.miraeldev.data.repository.FilterDataRepositoryImpl
import com.miraeldev.data.repository.SearchAnimeDataRepositoryImpl
import com.miraeldev.data.repository.UserAuthDataRepositoryImpl
import com.miraeldev.data.repository.UserDataRepositoryImpl
import com.miraeldev.data.repository.VideoPlayerRepositoryImpl
import com.miraeldev.di.qualifiers.AuthClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindAnimeListRepository(impl: AnimeListDataRepositoryImpl): AnimeListDataRepository

    @Binds
    @Singleton
    abstract fun bindSearchAnimeDataRepository(impl: SearchAnimeDataRepositoryImpl): SearchAnimeDataRepository

    @Binds
    @Singleton
    abstract fun bindFilterDataRepository(impl: FilterDataRepositoryImpl): FilterAnimeDataRepository

    @Binds
    @Singleton
    abstract fun bindAnimeDetailDataRepository(impl: AnimeDetailDataRepositoryImpl): AnimeDetailDataRepository


    @Binds
    @Singleton
    abstract fun bindVideoPlayerDataRepository(impl: VideoPlayerRepositoryImpl): VideoPlayerDataRepository

    @Binds
    @Singleton
    abstract fun bindFavouriteAnimeDataRepository(impl: FavouriteAnimeDataRepositoryImpl): FavouriteAnimeDataRepository

    @Binds
    @Singleton
    abstract fun bindUserDataRepository(impl: UserDataRepositoryImpl): UserDataRepository

    @Binds
    @Singleton
    abstract fun bindUserApiService(impl: UserApiServiceImpl): UserApiService

    @Binds
    @Singleton
    abstract fun bindDownloader(impl: AndroidDownloader): Downloader


    @UnstableApi
    companion object {

        private const val PLAYER_SEEK_BACK_INCREMENT = 10 * 1000L
        private const val PLAYER_SEEK_FORWARD_INCREMENT = 10 * 1000L

        @Provides
        @Singleton
        fun provideUserAuthRepositoryImpl(
            @AuthClient client: HttpClient,
            localService: LocalTokenService,
            userRepository: UserDataRepository,
            @ApplicationContext context: Context
        ): UserAuthDataRepository {
            return UserAuthDataRepositoryImpl(
                client = client,
                localService = localService,
                userDataRepository = userRepository,
                context = context
            )
        }

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
        fun providePreferenceManager(@ApplicationContext context: Context): PreferenceDataStoreAPI {
            return PreferenceManager(context)
        }

        @Provides
        @Singleton
        fun provideLocalUserManager(@ApplicationContext context: Context): LocalUserStoreApi {
            return LocalUserManager(context)
        }

        @Provides
        @Singleton
        fun provideAndroidDownloader(
            @ApplicationContext context: Context,
            preferenceManager: PreferenceDataStoreAPI
        ): AndroidDownloader {
            return AndroidDownloader(context, preferenceManager)
        }
    }

}