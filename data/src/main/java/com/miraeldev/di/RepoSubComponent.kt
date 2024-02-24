package com.miraeldev.di

import com.miraeldev.AnimeDetailDataRepository
import com.miraeldev.AnimeListDataRepository
import com.miraeldev.Downloader
import com.miraeldev.FavouriteAnimeDataRepository
import com.miraeldev.FilterAnimeDataRepository
import com.miraeldev.ForgotPasswordDataRepository
import com.miraeldev.HomeDataRepository
import com.miraeldev.LocalUserDataRepository
import com.miraeldev.SearchAnimeDataRepository
import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.VideoPlayerDataRepository
import com.miraeldev.data.remote.userApiService.UserApiService
import com.miraeldev.di.scope.Singleton


interface RepoSubComponent {

    @Singleton
    val animeListDataRepository: AnimeListDataRepository

    @Singleton
    val searchAnimeDataRepository: SearchAnimeDataRepository

    @Singleton
    val filterAnimeDataRepository: FilterAnimeDataRepository

    @Singleton
    val animeDetailDataRepository: AnimeDetailDataRepository

    @Singleton
    val videoPlayerDataRepository: VideoPlayerDataRepository

    @Singleton
    val forgotPasswordDataRepository: ForgotPasswordDataRepository

    @Singleton
    val favouriteAnimeDataRepository: FavouriteAnimeDataRepository

    @Singleton
    val userDataRepository: UserDataRepository

    @Singleton
    val userAuthDataRepository: UserAuthDataRepository

    @Singleton
    val homeDataRepository: HomeDataRepository

    @Singleton
    val localUserDataRepository: LocalUserDataRepository

    @Singleton
    val userApiService: UserApiService

    @Singleton
    val downloader: Downloader
}