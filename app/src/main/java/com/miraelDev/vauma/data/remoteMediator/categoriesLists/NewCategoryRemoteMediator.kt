package com.miraelDev.vauma.data.remoteMediator.categoriesLists

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.miraelDev.vauma.data.local.AppDatabase
import com.miraelDev.vauma.data.local.models.newCategory.NewCategoryRemoteKeys
import com.miraelDev.vauma.data.remote.ApiRoutes
import com.miraelDev.vauma.data.remote.NetworkHandler
import com.miraelDev.vauma.data.remote.dto.Response
import com.miraelDev.vauma.data.remote.dto.mapToNewCategoryModel
import com.miraelDev.vauma.domain.models.AnimeInfo
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class NewCategoryRemoteMediator(
    private val appDatabase: AppDatabase,

    private val client: HttpClient,

    private val networkHandler: NetworkHandler

) : RemoteMediator<Int, AnimeInfo>() {


    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (appDatabase.newCategoryRemoteKeys()
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
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
//                val remoteKeys = getRemoteKeyForFirstItem(state)
//                val prevKey = remoteKeys?.prevKey
//                prevKey
//                    ?:
//                remoteKeys != null
                    return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)

                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {

            val apiResponse = client.get<Response>(
                "${ApiRoutes.GET_NEW_CATEGORY_LIST}page_num=$page&page_size=20"
            )


            Log.d(
                "tag",
                apiResponse.toString()
            )

            val anime = apiResponse.results.map { it.mapToNewCategoryModel() }
            val endOfPaginationReached = anime.isEmpty()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.newCategoryRemoteKeys().clearRemoteKeys()
                    appDatabase.newCategoryDao().clearAllAnime()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = anime.map {
                    NewCategoryRemoteKeys(
                        animeId = it.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                appDatabase.newCategoryRemoteKeys().insertAll(remoteKeys)
                appDatabase.newCategoryDao()
                    .insertAll(anime.onEachIndexed { ind, anime -> anime.page = page })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: Exception) {
            delay(1000)
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, AnimeInfo>): NewCategoryRemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                appDatabase.newCategoryRemoteKeys().getRemoteKeyByAnimeId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AnimeInfo>): NewCategoryRemoteKeys? {

        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { anime ->
            appDatabase.newCategoryRemoteKeys().getRemoteKeyByAnimeId(anime.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AnimeInfo>): NewCategoryRemoteKeys? {

        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { anime ->
            appDatabase.newCategoryRemoteKeys().getRemoteKeyByAnimeId(anime.id)
        }
    }
}