package com.miraeldev.data.repository

import com.miraeldev.FavouriteAnimeDataRepository
import com.miraeldev.HomeDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.anime.toLastWatched
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.local.AppDatabase
import com.miraeldev.data.remote.ApiRoutes
import com.miraeldev.data.remote.dto.Response
import com.miraeldev.data.remote.dto.mapToFilmCategoryModel
import com.miraeldev.data.remote.dto.mapToNameCategoryModel
import com.miraeldev.data.remote.dto.mapToNewCategoryModel
import com.miraeldev.data.remote.dto.mapToPopularCategoryModel
import com.miraeldev.di.qualifiers.CommonHttpClient
import com.miraeldev.user.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class HomeDataRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    @CommonHttpClient private val client: HttpClient,
    private val localTokenService: LocalTokenService,
    private val userDataRepository: UserDataRepository
) : HomeDataRepository {
    override suspend fun loadData() {

        val bearerToken = localTokenService.getBearerToken()

        val isNewEmpty = appDatabase.newCategoryDao().isEmpty()

        val firstDbModel = appDatabase.newCategoryDao().getCreateTime()

        if (isNewEmpty) {
            loadNewAnimeList(bearerToken)
            loadPopularAnimeList(bearerToken)
            loadNameAnimeList(bearerToken)
            loadFilmAnimeList(bearerToken)
        } else if (System.currentTimeMillis() - firstDbModel.createTime > FIVE_HOURS_IN_MILLIS) {
            loadNewAnimeList(bearerToken)
            loadPopularAnimeList(bearerToken)
            loadNameAnimeList(bearerToken)
            loadFilmAnimeList(bearerToken)
        }
    }

    private suspend fun loadNewAnimeList(bearerToken: String?) {
        val apiResponse = client.get {
            url("${ApiRoutes.GET_POPULAR_CATEGORY_LIST_ROUTE}page=0&page_size=15")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
            .body<Response>()

        val anime = apiResponse.results.map { it.mapToPopularCategoryModel() }

        appDatabase.popularCategoryDao().insertAll(anime)
    }

    private suspend fun loadPopularAnimeList(bearerToken: String?) {
        val apiResponse = client.get {
            url("${ApiRoutes.GET_NEW_CATEGORY_LIST_ROUTE}page=0&page_size=15")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
            .body<Response>()

        val anime = apiResponse.results.map { it.mapToNewCategoryModel() }

        appDatabase.newCategoryDao().insertAll(anime)
    }

    private suspend fun loadNameAnimeList(bearerToken: String?) {
        val apiResponse = client.get {
            url("${ApiRoutes.GET_NAME_CATEGORY_LIST_ROUTE}page=0&page_size=15")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
            .body<Response>()

        val anime = apiResponse.results.map { it.mapToNameCategoryModel() }

        appDatabase.nameCategoryDao().insertAll(anime)
    }

    private suspend fun loadFilmAnimeList(bearerToken: String?) {
        val apiResponse = client.get {
            url("${ApiRoutes.GET_FILMS_CATEGORY_LIST_ROUTE}page=0&page_size=15")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
            .body<Response>()

        val anime = apiResponse.results.map { it.mapToFilmCategoryModel() }

        appDatabase.filmCategoryDao().insertAll(anime)
    }


    override fun getNewAnimeList(): Flow<List<AnimeInfo>> {
        return appDatabase.newCategoryDao().getAnimeList()
    }


    override fun getPopularAnimeList(): Flow<List<AnimeInfo>> {
        return appDatabase.popularCategoryDao().getAnimeList()
    }

    override fun getNameAnimeList(): Flow<List<AnimeInfo>> {
        return appDatabase.nameCategoryDao().getAnimeList()
    }

    override fun getFilmsAnimeList(): Flow<List<AnimeInfo>> {
        return appDatabase.filmCategoryDao().getAnimeList()
    }

    override fun getUserInfo(): Flow<User> {
        return userDataRepository.getUserInfo()
            .map { user ->
                if (user.lastWatchedAnime == null) {
                    val animeInfo = getPopularAnimeList().first()[0]
                    user.copy(lastWatchedAnime = animeInfo.toLastWatched())
                } else {
                    user
                }
            }
    }


    companion object {
        private const val FIVE_HOURS_IN_MILLIS = 18000000
    }

}