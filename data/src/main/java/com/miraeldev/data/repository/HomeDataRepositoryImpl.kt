package com.miraeldev.data.repository

import com.miraeldev.HomeDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.local.AppDatabase
import com.miraeldev.data.remote.ApiResult
import com.miraeldev.data.remote.ApiRoutes
import com.miraeldev.data.remote.dto.Response
import com.miraeldev.data.remote.dto.mapToFilmCategoryModel
import com.miraeldev.data.remote.dto.mapToNameCategoryModel
import com.miraeldev.data.remote.dto.mapToNewCategoryModel
import com.miraeldev.data.remote.dto.mapToPopularCategoryModel
import com.miraeldev.data.remote.dto.toAnimeDetailInfo
import com.miraeldev.data.remote.dto.toAnimeInfo
import com.miraeldev.data.remote.dto.toLastWatched
import com.miraeldev.data.remote.searchApi.SearchApiService
import com.miraeldev.di.AppHttpClient
import com.miraeldev.user.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class HomeDataRepositoryImpl internal constructor(
    private val appDatabase: AppDatabase,
    private val client: AppHttpClient,
    private val searchApiService: SearchApiService,
    private val localTokenService: LocalTokenService,
    private val userDataRepository: UserDataRepository
) : HomeDataRepository {
    override suspend fun loadData(): Map<Int, List<AnimeInfo>> {

        val bearerToken = localTokenService.getBearerToken()

        val isNewEmpty = appDatabase.newCategoryDao().isEmpty()

        val firstDbModel = appDatabase.newCategoryDao().getCreateTime()

        return when {
            isNewEmpty || System.currentTimeMillis() - firstDbModel.createTime > FIVE_HOURS_IN_MILLIS -> {
                val map = mutableMapOf<Int, List<AnimeInfo>>()
                map[0] = loadNewAnimeList(bearerToken)
                map[1] = loadPopularAnimeList(bearerToken)
                map[2] = loadNameAnimeList(bearerToken)
                map[3] = loadFilmAnimeList(bearerToken)
                map
            }

            else -> {
                getAnimeListForCategory()
            }
        }
    }

    private suspend fun loadNewAnimeList(bearerToken: String?): List<AnimeInfo> {
        val apiResponse = client.get {
            url("${ApiRoutes.GET_NEW_CATEGORY_LIST_ROUTE}page=0&page_size=15")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
            .body<Response>()

        val anime = apiResponse.results.map { it.mapToNewCategoryModel() }

        appDatabase.newCategoryDao().insertAll(anime)

        return apiResponse.results.map { it.toAnimeInfo() }
    }

    private suspend fun loadPopularAnimeList(bearerToken: String?): List<AnimeInfo> {
        val apiResponse = client.get {
            url("${ApiRoutes.GET_POPULAR_CATEGORY_LIST_ROUTE}page=0&page_size=15")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
            .body<Response>()

        val anime = apiResponse.results.map { it.mapToPopularCategoryModel() }

        appDatabase.popularCategoryDao().insertAll(anime)

        return apiResponse.results.map { it.toAnimeInfo() }
    }

    private suspend fun loadNameAnimeList(bearerToken: String?): List<AnimeInfo> {
        val apiResponse = client.get {
            url("${ApiRoutes.GET_NAME_CATEGORY_LIST_ROUTE}page=0&page_size=15")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
            .body<Response>()

        val anime = apiResponse.results.map { it.mapToNameCategoryModel() }

        appDatabase.nameCategoryDao().insertAll(anime)

        return apiResponse.results.map { it.toAnimeInfo() }
    }

    private suspend fun loadFilmAnimeList(bearerToken: String?): List<AnimeInfo> {
        val apiResponse = client.get {
            url("${ApiRoutes.GET_FILMS_CATEGORY_LIST_ROUTE}page=0&page_size=15")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
            .body<Response>()

        val anime = apiResponse.results.map { it.mapToFilmCategoryModel() }

        appDatabase.filmCategoryDao().insertAll(anime)

        return apiResponse.results.map { it.toAnimeInfo() }
    }


    private suspend fun getAnimeListForCategory(): Map<Int, List<AnimeInfo>> {

        val newAnimeList = appDatabase.newCategoryDao().getAnimeList()
        val popularAnimeList = appDatabase.popularCategoryDao().getAnimeList()
        val nameAnimeList = appDatabase.nameCategoryDao().getAnimeList()
        val filmsAnimeList = appDatabase.filmCategoryDao().getAnimeList()

        val map = mutableMapOf<Int, List<AnimeInfo>>()

        map[0] = newAnimeList
        map[1] = popularAnimeList
        map[2] = nameAnimeList
        map[3] = filmsAnimeList

        return map
    }

    override fun getUserInfo(): Flow<User> {
        return flow { emit(userDataRepository.getUserInfo()) }
            .map { user ->
                if (user.lastWatchedAnime == null) {
                    val animeId = appDatabase.popularCategoryDao().getAnimeList()[0].id
                    when (val apiResult = searchApiService.getAnimeById(animeId)) {
                        is ApiResult.Success -> {
                            val animeList = apiResult.animeList.map { it.toAnimeDetailInfo() }
                            user.copy(lastWatchedAnime = animeList[0].toLastWatched())
                        }

                        is ApiResult.Failure -> {
                            user
                        }
                    }

                } else {
                    user
                }
            }
    }


    companion object {
        private const val FIVE_HOURS_IN_MILLIS = 24 * 60 * 60 * 1000
    }

}