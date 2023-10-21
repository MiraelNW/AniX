package com.miraelDev.vauma.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.miraelDev.vauma.data.dataStore.tokenService.LocalTokenService
import com.miraelDev.vauma.data.local.AppDatabase
import com.miraelDev.vauma.data.remote.NetworkHandler
import com.miraelDev.vauma.data.remoteMediator.categoriesLists.FilmCategoryRemoteMediator
import com.miraelDev.vauma.data.remoteMediator.categoriesLists.NameCategoryRemoteMediator
import com.miraelDev.vauma.data.remoteMediator.categoriesLists.NewCategoryRemoteMediator
import com.miraelDev.vauma.data.remoteMediator.categoriesLists.PopularCategoryRemoteMediator
import com.miraelDev.vauma.di.qualifiers.CommonHttpClient
import com.miraelDev.vauma.domain.models.anime.AnimeInfo
import com.miraelDev.vauma.domain.repository.AnimeListRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AnimeListRepositoryImpl @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val appDatabase: AppDatabase,
    @CommonHttpClient private val httpClient: HttpClient,
    private val localTokenService: LocalTokenService
) : AnimeListRepository {


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