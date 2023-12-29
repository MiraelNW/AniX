package com.miraeldev.data.remote.searchApi

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.remote.ApiRoutes
import com.miraeldev.data.remote.NetworkHandler
import com.miraeldev.data.remote.dto.Response
import com.miraeldev.data.remote.dto.toAnimeInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.errors.IOException

internal class SearchPagingPagingSource(
    private val name: String,
    private val yearFilter: String?,
    private val sortFilter: String?,
    private val genreListFilter: List<String>,
    private val client: HttpClient,
    private val networkHandler: NetworkHandler,
    private val localTokenService: LocalTokenService
) : PagingSource<Int, AnimeInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeInfo> {
        return try {

            if (!networkHandler.isConnected.value) {
                return LoadResult.Error(IOException())
            }

            val genreCode = getGenreCodeFromList(genreListFilter)

            val yearCode = getYearCode(yearFilter)

            val sortCode = getSortCode(sortFilter)

            val page = params.key ?: 0
            val pageSize = params.loadSize.coerceAtMost(20)

            val bearerToken = localTokenService.getBearerToken()

            val response =
                if (yearFilter != null && sortFilter != null && genreListFilter.isNotEmpty()) {
                    client.get {
                        url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&sort=$sortCode&date=$yearCode&genres=$genreCode&page=$page&page_size=$pageSize")
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $bearerToken")
                        }
                    }
                        .body()
                } else if (yearCode != null && sortFilter != null && genreListFilter.isEmpty()) {
                    client.get {
                        url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&sort=$sortCode&date=$yearCode&page=$page&page_size=$pageSize")
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $bearerToken")
                        }
                    }
                        .body<Response>()
                } else if (yearCode != null && sortFilter == null && genreListFilter.isNotEmpty()) {
                    client.get {
                        url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&date=$yearCode&genres=$genreCode&page=$page&page_size=$pageSize")
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $bearerToken")
                        }
                    }
                        .body<Response>()
                } else if (yearCode == null && sortFilter != null && genreListFilter.isNotEmpty()) {
                    client.get {
                        url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&sort=$sortCode&genres=$genreCode&page=$page&page_size=$pageSize")
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $bearerToken")
                        }
                    }
                        .body<Response>()
                } else if (yearCode != null && sortFilter == null && genreListFilter.isEmpty()) {
                    client.get {
                        url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&date=$yearCode&page=$page&page_size=$pageSize")
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $bearerToken")
                        }
                    }
                        .body<Response>()
                } else if (yearCode == null && sortFilter != null && genreListFilter.isEmpty()) {
                    client.get {
                        url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&sort=$sortCode&page=$page&page_size=$pageSize")
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $bearerToken")
                        }
                    }
                        .body<Response>()
                } else if (yearCode == null && sortFilter == null && genreListFilter.isNotEmpty()) {
                    client.get {
                        url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&genres=$genreCode&page=$page&page_size=$pageSize")
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $bearerToken")
                        }
                    }
                        .body<Response>()
                } else {
                    client.get {
                        url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&page=$page&page_size=$pageSize")
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $bearerToken")
                        }
                    }
                        .body<Response>()
                }



            LoadResult.Page(
                data = response.results.map { it.toAnimeInfo() },
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (response.isLast) null else page.plus(1),
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
                "h"
            }

            "2023" -> {
                "g"
            }

            "2022" -> {
                "f"
            }

            "2021" -> {
                "e"
            }

            "2015-2020" -> {
                "d"
            }

            "2008-2014" -> {
                "c"
            }

            "2000-2007" -> {
                "b"
            }

            "до 2000" -> {
                "a"
            }

            else -> {
                "a"
            }
        }
    }

    private fun getSortCode(sort: String?): String? {
        if (sort == null) return null
        return when (sort) {

            "Алфавиту" -> {
                "name"
            }

            "Рейтингу" -> {
                "popular"
            }

            "Количеству серий" -> {
                "c"
            }

            "Году выхода" -> {
                "new"
            }

            else -> {
                "new"
            }
        }
    }


}