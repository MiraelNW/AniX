package com.miraelDev.hikari.data.Repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.miraelDev.hikari.data.local.AppDatabase
import com.miraelDev.hikari.data.local.models.newCategory.PopularCategoryAnimeInfoDbModel
import com.miraelDev.hikari.data.remote.NetworkHandler
import com.miraelDev.hikari.data.remote.categoriesLists.FilmCategoryRemoteMediator
import com.miraelDev.hikari.data.remote.categoriesLists.NameCategoryRemoteMediator
import com.miraelDev.hikari.data.remote.categoriesLists.NewCategoryRemoteMediator
import com.miraelDev.hikari.data.remote.categoriesLists.PopularCategoryRemoteMediator
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.AnimeListRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AnimeListRepositoryImpl @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val appDatabase: AppDatabase,
    private val httpClient: HttpClient
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
                networkHandler = networkHandler
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
                networkHandler = networkHandler
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
                networkHandler = networkHandler
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
                networkHandler = networkHandler
            )
        ).flow

    }

}