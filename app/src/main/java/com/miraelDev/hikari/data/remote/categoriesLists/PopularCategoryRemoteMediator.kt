package com.miraelDev.hikari.data.remote.categoriesLists

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.miraelDev.hikari.data.local.AppDatabase
import com.miraelDev.hikari.data.local.models.newCategory.PopularCategoryRemoteKeys
import com.miraelDev.hikari.data.remote.ApiRoutes
import com.miraelDev.hikari.data.remote.NetworkHandler
import com.miraelDev.hikari.data.remote.dto.Response
import com.miraelDev.hikari.data.remote.dto.mapToNewCategoryModel
import com.miraelDev.hikari.data.remote.dto.mapToPopularCategoryModel
import com.miraelDev.hikari.domain.models.AnimeInfo
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagingApi::class)
class PopularCategoryRemoteMediator(
    private val appDatabase: AppDatabase,
    private val client: HttpClient,
    private val networkHandler: NetworkHandler

) : RemoteMediator<Int, AnimeInfo>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeInfo>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {

                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
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

            val apiResponse = client.get<Response>(
                "${ApiRoutes.GET_POPULAR_CATEGORY_LIST}page_num=$page&page_size=2"
            )

            Log.d("tag","request"+"${ApiRoutes.GET_POPULAR_CATEGORY_LIST}page_num=$page&page_size=2")

            delay(3000L) //TODO For testing only!

            val anime = apiResponse.results.map { it.mapToPopularCategoryModel() }
            val endOfPaginationReached = anime.isEmpty()



            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.popularCategoryRemoteKeysDao().clearRemoteKeys()
                    appDatabase.popularCategoryDao().clearAllAnime()
                }
                val prevKey = if (page > 1) page - 1 else null
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
                appDatabase.popularCategoryDao()
                    .insertAll(anime.onEachIndexed { _, anime -> anime.page = page })
                Log.d("tag","in trans"+anime.toString())
                Log.d("tag", "from mediator " + state.pages.toString())
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: Exception) {
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