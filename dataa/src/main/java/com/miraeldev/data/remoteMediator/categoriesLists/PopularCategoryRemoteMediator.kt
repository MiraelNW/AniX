package com.miraeldev.data.remoteMediator.categoriesLists

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.remote.NetworkHandler
import com.miraeldev.data.remote.dto.Response
import com.miraeldev.data.remote.dto.mapToPagingPopularCategoryModel
import com.miraeldev.local.AppDatabase
import com.miraeldev.local.models.popularCategory.PopularCategoryRemoteKeys
import com.miraeldev.api.AppNetworkClient
import io.ktor.client.call.body
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
internal class PopularCategoryRemoteMediator(
    private val appDatabase: AppDatabase,
    private val appNetworkClient: com.miraeldev.api.AppNetworkClient,
    private val networkHandler: NetworkHandler
) : RemoteMediator<Int, AnimeInfo>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (appDatabase.popularCategoryRemoteKeysDao()
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

            val apiResponse = appNetworkClient.getPopularCategoryList(page).body<Response>()

            val anime = apiResponse.results.map { it.mapToPagingPopularCategoryModel() }
            val endOfPaginationReached =
                anime.isEmpty() ||  apiResponse.isLast

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.popularCategoryRemoteKeysDao().clearRemoteKeys()
                    appDatabase.popularCategoryPagingDao().clearAllAnime()
                }
                val prevKey = if (page > 0) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = anime.map {
                    PopularCategoryRemoteKeys(
                        animeId = it.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }
                appDatabase.popularCategoryRemoteKeysDao().insertAll(remoteKeys)
                appDatabase.popularCategoryPagingDao()
                    .insertAll(anime.onEachIndexed { _, anime -> anime.page = page })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: Exception) {
            delay(1000)
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, AnimeInfo>): PopularCategoryRemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                appDatabase.popularCategoryRemoteKeysDao().getRemoteKeyByAnimeId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AnimeInfo>): PopularCategoryRemoteKeys? {

        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { anime ->
            appDatabase.popularCategoryRemoteKeysDao().getRemoteKeyByAnimeId(anime.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AnimeInfo>): PopularCategoryRemoteKeys? {

        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { anime ->
            appDatabase.popularCategoryRemoteKeysDao().getRemoteKeyByAnimeId(anime.id)
        }
    }
}