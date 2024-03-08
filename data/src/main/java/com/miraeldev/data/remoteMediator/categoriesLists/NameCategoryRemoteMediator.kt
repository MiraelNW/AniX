package com.miraeldev.data.remoteMediator.categoriesLists

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.remote.NetworkHandler
import com.miraeldev.data.remote.dto.Response
import com.miraeldev.data.remote.dto.mapToPagingNameCategoryModel
import com.miraeldev.local.AppDatabase
import com.miraeldev.local.models.nameCategory.NameCategoryRemoteKeys
import com.miraeldev.network.AppNetworkClient
import io.ktor.client.call.body
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
internal class NameCategoryRemoteMediator(
    private val appDatabase: AppDatabase,
    private val appNetworkClient: AppNetworkClient,
    private val networkHandler: NetworkHandler

) : RemoteMediator<Int, AnimeInfo>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (appDatabase.nameCategoryRemoteKeys()
                .getCreationTime() ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeInfo>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {

                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)

                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            if (!networkHandler.isConnected.value) {
                delay(1000)
                return MediatorResult.Error(IOException())
            }

            val apiResponse = appNetworkClient.getNameCategoryList(page).body<Response>()

            val anime = apiResponse.results.map { it.mapToPagingNameCategoryModel() }

            val endOfPaginationReached = anime.isEmpty() ||  apiResponse.isLast

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.nameCategoryRemoteKeys().clearRemoteKeys()
                    appDatabase.nameCategoryPagingDao().clearAllAnime()
                }
                val prevKey = if (page > 0) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = anime.map {
                    NameCategoryRemoteKeys(
                        animeId = it.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                appDatabase.nameCategoryRemoteKeys().insertAll(remoteKeys)
                appDatabase.nameCategoryPagingDao()
                    .insertAll(anime.onEachIndexed { _, movie -> movie.page = page })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: Exception) {
            delay(1000)
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, AnimeInfo>): NameCategoryRemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                appDatabase.nameCategoryRemoteKeys().getRemoteKeyByAnimeId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AnimeInfo>): NameCategoryRemoteKeys? {

        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            appDatabase.nameCategoryRemoteKeys().getRemoteKeyByAnimeId(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AnimeInfo>): NameCategoryRemoteKeys? {

        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            appDatabase.nameCategoryRemoteKeys().getRemoteKeyByAnimeId(movie.id)
        }
    }
}