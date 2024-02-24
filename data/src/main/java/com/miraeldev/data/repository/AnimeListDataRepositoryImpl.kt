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
import com.miraeldev.di.AppHttpClient
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@OptIn(ExperimentalPagingApi::class)
@Inject
class AnimeListDataRepositoryImpl internal constructor(
    private val networkHandler: NetworkHandler,
    private val appDatabase: AppDatabase,
    private val httpClient: AppHttpClient,
    private val localTokenService: LocalTokenService
) : AnimeListDataRepository {


    override fun getPagingNewAnimeList(): Flow<PagingData<AnimeInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { appDatabase.newCategoryPagingDao().getAnime() },
            remoteMediator = NewCategoryRemoteMediator(
                client = httpClient,
                appDatabase = appDatabase,
                networkHandler = networkHandler,
                localTokenService = localTokenService
            )
        )
            .flow

    }


    override fun getPagingPopularAnimeList(): Flow<PagingData<AnimeInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
            ),
            pagingSourceFactory = { appDatabase.popularCategoryPagingDao().getAnime() },
            remoteMediator = PopularCategoryRemoteMediator(
                client = httpClient,
                appDatabase = appDatabase,
                networkHandler = networkHandler,
                localTokenService = localTokenService
            )
        ).flow



    }

    override fun getPagingNameAnimeList(): Flow<PagingData<AnimeInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { appDatabase.nameCategoryPagingDao().getAnime() },
            remoteMediator = NameCategoryRemoteMediator(
                client = httpClient,
                appDatabase = appDatabase,
                networkHandler = networkHandler,
                localTokenService = localTokenService
            )
        ).flow

    }

    override fun getPagingFilmsAnimeList(): Flow<PagingData<AnimeInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { appDatabase.filmCategoryPagingDao().getAnime() },
            remoteMediator = FilmCategoryRemoteMediator(
                client = httpClient,
                appDatabase = appDatabase,
                networkHandler = networkHandler,
                localTokenService = localTokenService
            )
        ).flow

    }

}