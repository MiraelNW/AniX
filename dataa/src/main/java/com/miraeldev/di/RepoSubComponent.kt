package com.miraeldev.di

import com.miraeldev.AnimeDetailDataRepository
import com.miraeldev.AnimeListDataRepository
import com.miraeldev.Downloader
import com.miraeldev.FavouriteAnimeDataRepository
import com.miraeldev.FilterAnimeDataRepository
import com.miraeldev.ForgotPasswordDataRepository
import com.miraeldev.HomeDataRepository
import com.miraeldev.SearchAnimeDataRepository
import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.VideoPlayerDataRepository
import com.miraeldev.data.downloadMananger.AndroidDownloader
import com.miraeldev.data.repository.AnimeDetailDataRepositoryImpl
import com.miraeldev.data.repository.AnimeListDataRepositoryImpl
import com.miraeldev.data.repository.FavouriteAnimeDataRepositoryImpl
import com.miraeldev.data.repository.FilterDataRepositoryImpl
import com.miraeldev.data.repository.ForgotPasswordDataRepositoryImpl
import com.miraeldev.data.repository.HomeDataRepositoryImpl
import com.miraeldev.data.repository.SearchAnimeDataRepositoryImpl
import com.miraeldev.data.repository.UserAuthDataRepositoryImpl
import com.miraeldev.data.repository.UserDataRepositoryImpl
import com.miraeldev.data.repository.VideoPlayerRepositoryImpl
import com.miraeldev.models.di.scope.Singleton
import me.tatarka.inject.annotations.Provides

interface RepoSubComponent {

    @Singleton
    @Provides
    fun AnimeListDataRepositoryImpl.bind(): AnimeListDataRepository = this

    @Singleton
    @Provides
    fun SearchAnimeDataRepositoryImpl.bind(): SearchAnimeDataRepository = this

    @Singleton
    @Provides
    fun FilterDataRepositoryImpl.bind(): FilterAnimeDataRepository = this

    @Singleton
    @Provides
    fun AnimeDetailDataRepositoryImpl.bind(): AnimeDetailDataRepository = this

    @Singleton
    @Provides
    fun VideoPlayerRepositoryImpl.bind(): VideoPlayerDataRepository = this

    @Singleton
    @Provides
    fun ForgotPasswordDataRepositoryImpl.bind(): ForgotPasswordDataRepository = this

    @Singleton
    @Provides
    fun FavouriteAnimeDataRepositoryImpl.bind(): FavouriteAnimeDataRepository = this

    @Singleton
    @Provides
    fun UserDataRepositoryImpl.bind(): UserDataRepository = this

    @Singleton
    @Provides
    fun UserAuthDataRepositoryImpl.bind(): UserAuthDataRepository = this

    @Singleton
    @Provides
    fun HomeDataRepositoryImpl.bind(): HomeDataRepository = this

    @Singleton
    @Provides
    fun AndroidDownloader.bind(): Downloader = this
}