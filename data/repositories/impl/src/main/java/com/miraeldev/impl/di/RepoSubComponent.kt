package com.miraeldev.di

import com.miraeldev.api.AnimeDetailDataRepository
import com.miraeldev.api.AnimeListDataRepository
import com.miraeldev.api.Downloader
import com.miraeldev.api.FavouriteAnimeDataRepository
import com.miraeldev.api.FilterAnimeDataRepository
import com.miraeldev.api.ForgotPasswordDataRepository
import com.miraeldev.api.HomeDataRepository
import com.miraeldev.api.SearchAnimeDataRepository
import com.miraeldev.api.UserAuthDataRepository
import com.miraeldev.api.UserDataRepository
import com.miraeldev.api.VideoPlayerDataRepository
import com.miraeldev.impl.repository.AnimeDetailDataRepositoryImpl
import com.miraeldev.impl.repository.AnimeListDataRepositoryImpl
import com.miraeldev.impl.repository.FavouriteAnimeDataRepositoryImpl
import com.miraeldev.data.repository.FilterDataRepositoryImpl
import com.miraeldev.impl.repository.ForgotPasswordDataRepositoryImpl
import com.miraeldev.impl.repository.HomeDataRepositoryImpl
import com.miraeldev.impl.repository.SearchAnimeDataRepositoryImpl
import com.miraeldev.data.repository.UserAuthDataRepositoryImpl
import com.miraeldev.impl.repository.UserDataRepositoryImpl
import com.miraeldev.data.repository.VideoPlayerRepositoryImpl
import com.miraeldev.impl.downloadMananger.AndroidDownloader
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