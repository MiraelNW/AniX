package com.miraeldev.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.miraeldev.AnimeListDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.local.AppDatabase
import com.miraeldev.data.remote.NetworkHandler
import com.miraeldev.data.remoteMediator.categoriesLists.FilmCategoryRemoteMediator
import com.miraeldev.data.remoteMediator.categoriesLists.NameCategoryRemoteMediator
import com.miraeldev.data.remoteMediator.categoriesLists.NewCategoryRemoteMediator
import com.miraeldev.data.remoteMediator.categoriesLists.PopularCategoryRemoteMediator
import com.miraeldev.di.qualifiers.CommonHttpClient
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
internal class AnimeListDataRepositoryImpl @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val appDatabase: AppDatabase,
    @CommonHttpClient private val httpClient: HttpClient,
    private val localTokenService: LocalTokenService
) : AnimeListDataRepository {


    override fun getNewAnimeList(): Flow<PagingData<AnimeInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { appDatabase.newCategoryDao().getAnime() },
            remoteMediator = NewCategoryRemoteMediator(
                client = httpClient,
                appDatabase = appDatabase,
                networkHandler = networkHandler,
                localTokenService = localTokenService
            )
        )
            .flow

    }


    override fun getPopularAnimeList(): Flow<PagingData<AnimeInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
            ),
            pagingSourceFactory = { appDatabase.popularCategoryDao().getAnime() },
            remoteMediator = PopularCategoryRemoteMediator(
                client = httpClient,
                appDatabase = appDatabase,
                networkHandler = networkHandler,
                localTokenService = localTokenService
            )
        ).flow



    }

    override fun getNameAnimeList(): Flow<PagingData<AnimeInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { appDatabase.nameCategoryDao().getAnime() },
            remoteMediator = NameCategoryRemoteMediator(
                client = httpClient,
                appDatabase = appDatabase,
                networkHandler = networkHandler,
                localTokenService = localTokenService
            )
        ).flow

    }

    override fun getFilmsAnimeList(): Flow<PagingData<AnimeInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { appDatabase.filmCategoryDao().getAnime() },
            remoteMediator = FilmCategoryRemoteMediator(
                client = httpClient,
                appDatabase = appDatabase,
                networkHandler = networkHandler,
                localTokenService = localTokenService
            )
        ).flow

    }

}