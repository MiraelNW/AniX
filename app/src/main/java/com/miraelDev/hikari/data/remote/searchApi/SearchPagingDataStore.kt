package com.miraelDev.hikari.data.remote.searchApi

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

class SearchPagingDataStore(
    private val name: String,
    private val yearFilter: String?,
    private val sortFilter: String?,
    private val genreListFilter: List<String>,
    private val client: HttpClient,
    private val networkHandler: NetworkHandler
) : PagingSource<Int, AnimeInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeInfo> {
        return try {

            if (!networkHandler.isConnected.value) {
                return LoadResult.Error(IOException())
            }

            val genreCode = getGenreCodeFromList(genreListFilter)

            val page = params.key ?: 1
            val pageSize = params.loadSize.coerceAtMost(20)

            val response =
                if (yearFilter != null && sortFilter != null && genreListFilter.isNotEmpty()) {
                    Log.d("tag","first cond")
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&date=$yearFilter&genre=$genreCode&page_num=$page&page_size=$pageSize"
                    )
                } else if (yearFilter != null && sortFilter != null && genreListFilter.isEmpty()) {
                    Log.d("tag","second cond")
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&date=$yearFilter&page_num=$page&page_size=$pageSize"
                    )
                } else if (yearFilter != null && sortFilter == null && genreListFilter.isNotEmpty()) {
                    Log.d("tag","third cond")
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&date=$yearFilter&genre=$genreCode&page_num=$page&page_size=$pageSize"
                    )
                } else if (yearFilter == null && sortFilter != null && genreListFilter.isNotEmpty()) {
                    Log.d("tag","fourth cond")
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&genre=$genreCode&page_num=$page&page_size=$pageSize"
                    )
                } else if (yearFilter != null && sortFilter == null && genreListFilter.isEmpty()) {
                    Log.d("tag","fifth cond")
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&date=$yearFilter&page_num=$page&page_size=$pageSize"
                    )
                } else if (yearFilter == null && sortFilter != null && genreListFilter.isEmpty()) {
                    Log.d("tag","sixth cond")
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&page_num=$page&page_size=$pageSize"
                    )
                } else if (yearFilter == null && sortFilter == null && genreListFilter.isNotEmpty()) {
                    Log.d("tag","seventh cond")
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&genre=$genreCode&page_num=$page&page_size=$pageSize"
                    )
                } else {
                    Log.d("tag","eighth cond")
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&page_num=$page&page_size=$pageSize"
                    )
                }


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

    private fun getGenreCodeFromList(genreList: List<String>): String {
        var codeResult = ""
        genreList.forEach { genre ->
            val numCode = when (genre) {

                "Боевик" -> {
                    "2"
                }

                "Триллер" -> {
                    "3"
                }

                "Детектив" -> {
                    "4"
                }

                "Драма" -> {
                    "5"
                }

                "Фантастика" -> {
                    "6"
                }

                "Фентези" -> {
                    "7"
                }

                "Мистика" -> {
                    "8"
                }

                "Психология" -> {
                    "9"
                }

                "Романтика" -> {
                    "10"
                }

                "Повседневность" -> {
                    "11"
                }

                "Комедия" -> {
                    "12"
                }

                else -> {
                    "1"
                }
            }

            codeResult += numCode
        }
        return codeResult
    }


}