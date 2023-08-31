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

class SearchPagingPagingSource(
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

            val yearCode = getYearCode(yearFilter)

            val page = params.key ?: 1
            val pageSize = params.loadSize.coerceAtMost(20)

            val response =
                if (yearFilter != null && sortFilter != null && genreListFilter.isNotEmpty()) {
                    Log.d(
                        "tag",
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&date=$yearCode&genre=$genreCode&page_num=$page&page_size=$pageSize"
                    )
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&date=$yearCode&genre=$genreCode&page_num=$page&page_size=$pageSize"
                    )
                } else if (yearCode != null && sortFilter != null && genreListFilter.isEmpty()) {
                    Log.d(
                        "tag",
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&date=$yearCode&page_num=$page&page_size=$pageSize"
                    )
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&date=$yearCode&page_num=$page&page_size=$pageSize"
                    )
                } else if (yearCode != null && sortFilter == null && genreListFilter.isNotEmpty()) {
                    Log.d(
                        "tag",
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&date=$yearCode&genre=$genreCode&page_num=$page&page_size=$pageSize"
                    )
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&date=$yearCode&genre=$genreCode&page_num=$page&page_size=$pageSize"
                    )
                } else if (yearCode == null && sortFilter != null && genreListFilter.isNotEmpty()) {
                    Log.d(
                        "tag",
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&genre=$genreCode&page_num=$page&page_size=$pageSize"
                    )
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&genre=$genreCode&page_num=$page&page_size=$pageSize"
                    )
                } else if (yearCode != null && sortFilter == null && genreListFilter.isEmpty()) {
                    Log.d(
                        "tag",
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&date=$yearCode&page_num=$page&page_size=$pageSize"
                    )
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&date=$yearCode&page_num=$page&page_size=$pageSize"
                    )
                } else if (yearCode == null && sortFilter != null && genreListFilter.isEmpty()) {
                    Log.d(
                        "tag",
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&page_num=$page&page_size=$pageSize"
                    )
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&page_num=$page&page_size=$pageSize"
                    )
                } else if (yearCode == null && sortFilter == null && genreListFilter.isNotEmpty()) {
                    Log.d(
                        "tag",
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&genre=$genreCode&page_num=$page&page_size=$pageSize"
                    )
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&genre=$genreCode&page_num=$page&page_size=$pageSize"
                    )
                } else {
                    Log.d(
                        "tag",
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&page_num=$page&page_size=$pageSize"
                    )
                    client.get<Response>(
                        "${ApiRoutes.SEARCH_URL_ANIME_LIST}${name}&page_num=$page&page_size=$pageSize"
                    )
                }

            LoadResult.Page(
                data = response.results.map { it.toAnimeInfo() },
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.next == null) null else page.plus(1),
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
            val litterCode = when (genre) {

                "Сенен" -> {
                    'a'
                }

                "Седзе" -> {
                    'b'
                }

                "Комедия" -> {
                    'c'
                }

                "Романтика" -> {
                    'd'
                }

                "Школа" -> {
                    'e'
                }

                "Боевые искусства" -> {
                    'f'
                }

                "Гарем" -> {
                    'g'
                }

                "Детектив" -> {
                    'h'
                }

                "Драма" -> {
                    'i'
                }

                "Повседневность" -> {
                    'j'
                }

                "Приключение" -> {
                    'k'
                }

                "Психологическое" -> {
                    'l'
                }

                "Сверхъестественное" -> {
                    'm'
                }

                "Спорт" -> {
                    'n'
                }

                "Ужасы" -> {
                    'o'
                }

                "Фантастика" -> {
                    'p'
                }

                "Фэнтези" -> {
                    'q'
                }

                "Экшен" -> {
                    'r'
                }

                "Триллер" -> {
                    's'
                }

                "Супер сила" -> {
                    't'
                }

                "Гурман" -> {
                    'u'
                }

                else -> {
                    'k'
                }
            }

            codeResult += litterCode
        }
        return codeResult
    }

    private fun getYearCode(year: String?): String? {
        if (year == null) return null
        return when (year) {

            "Онгоинг" -> {
                "a"
            }

            "2023" -> {
                "b"
            }

            "2022" -> {
                "c"
            }

            "2021" -> {
                "d"
            }

            "2015-2020" -> {
                "e"
            }

            "2008-2014" -> {
                "f"
            }

            "2000-2007" -> {
                "g"
            }

            "до 2000" -> {
                "h"
            }

            else -> {
                "k"
            }
        }
    }


}