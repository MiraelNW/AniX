package com.miraeldev.impl.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.api.AnimeListDataRepository
import com.miraeldev.api.AppNetworkClient
import com.miraeldev.data.remote.NetworkHandler
import com.miraeldev.data.remoteMediator.categoriesLists.FilmCategoryRemoteMediator
import com.miraeldev.data.remoteMediator.categoriesLists.NameCategoryRemoteMediator
import com.miraeldev.data.remoteMediator.categoriesLists.NewCategoryRemoteMediator
import com.miraeldev.data.remoteMediator.categoriesLists.PopularCategoryRemoteMediator
import com.miraeldev.local.AppDatabase
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@OptIn(ExperimentalPagingApi::class)
@Inject
class AnimeListDataRepositoryImpl(
    private val networkHandler: NetworkHandler,
    private val appDatabase: AppDatabase,
    private val appNetworkClient: AppNetworkClient
) : AnimeListDataRepository {


    override fun getPagingNewAnimeList(): Flow<PagingData<AnimeInfo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { appDatabase.newCategoryPagingDao().getAnime() },
            remoteMediator = NewCategoryRemoteMediator(
                appNetworkClient = appNetworkClient,
                appDatabase = appDatabase,
                networkHandler = networkHandler
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
                appNetworkClient = appNetworkClient,
                appDatabase = appDatabase,
                networkHandler = networkHandler
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
                appNetworkClient = appNetworkClient,
                appDatabase = appDatabase,
                networkHandler = networkHandler
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
                appNetworkClient = appNetworkClient,
                appDatabase = appDatabase,
                networkHandler = networkHandler
            )
        ).flow

    }

}