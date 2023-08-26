package com.miraelDev.hikari.data.remote.categoriesLists

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miraelDev.hikari.data.remote.ApiRoutes
import com.miraelDev.hikari.data.remote.NetworkHandler
import com.miraelDev.hikari.data.remote.dto.Response
import com.miraelDev.hikari.data.remote.dto.toAnimeInfo
import com.miraelDev.hikari.domain.models.AnimeInfo
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.delay

class PopularCategoryPagingDataStore(
    private val client: HttpClient,
    private val networkHandler: NetworkHandler
) : PagingSource<Int, AnimeInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeInfo> {
        return try {

            if (!networkHandler.isConnected.value) {
                return LoadResult.Error(IOException())
            }

            val page = params.key ?: 1
            val pageSize = params.loadSize.coerceAtMost(20)
            val response = client.get<Response>(
                "${ApiRoutes.GET_POPULAR_CATEGORY_LIST}page_num=$page&page_size=$pageSize"
            )
           LoadResult.Page(
                data = response.results.map { it.toAnimeInfo() },
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AnimeInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}