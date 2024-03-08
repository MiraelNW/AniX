package com.miraeldev.data.repository

import com.miraeldev.HomeDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.AppDatabase
import com.miraeldev.data.remote.dto.AnimeInfoDto
import com.miraeldev.data.remote.dto.Response
import com.miraeldev.data.remote.dto.mapToFilmCategoryModel
import com.miraeldev.data.remote.dto.mapToNameCategoryModel
import com.miraeldev.data.remote.dto.mapToNewCategoryModel
import com.miraeldev.data.remote.dto.mapToPopularCategoryModel
import com.miraeldev.data.remote.dto.toAnimeDetailInfo
import com.miraeldev.data.remote.dto.toAnimeInfo
import com.miraeldev.data.remote.dto.toLastWatched
import com.miraeldev.network.AppNetworkClient
import com.miraeldev.user.User
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import kotlin.collections.set

@Inject
class HomeDataRepositoryImpl(
    private val appNetworkClient: AppNetworkClient,
    private val appDatabase: AppDatabase,
    private val userDataRepository: UserDataRepository
) : HomeDataRepository {
    override suspend fun loadData(): Map<Int, List<AnimeInfo>> {
        val isNewEmpty = appDatabase.newCategoryDao().isEmpty()

        val firstDbModel = appDatabase.newCategoryDao().getCreateTime()

        return when {
            isNewEmpty || System.currentTimeMillis() - firstDbModel.createTime > FIVE_HOURS_IN_MILLIS -> {
                val map = mutableMapOf<Int, List<AnimeInfo>>()
                map[0] = loadNewAnimeList()
                map[1] = loadPopularAnimeList()
                map[2] = loadNameAnimeList()
                map[3] = loadFilmAnimeList()
                map
            }

            else -> {
                getAnimeListForCategory()
            }
        }
    }

    private suspend fun loadNewAnimeList(): List<AnimeInfo> {
        val apiResponse = appNetworkClient.getNewCategoryList(0).body<Response>()

        val anime = apiResponse.results.map { it.mapToNewCategoryModel() }

        appDatabase.newCategoryDao().insertAll(anime)

        return apiResponse.results.map { it.toAnimeInfo() }
    }

    private suspend fun loadPopularAnimeList(): List<AnimeInfo> {
        val apiResponse = appNetworkClient.getPopularCategoryList(0).body<Response>()

        val anime = apiResponse.results.map { it.mapToPopularCategoryModel() }

        appDatabase.popularCategoryDao().insertAll(anime)

        return apiResponse.results.map { it.toAnimeInfo() }
    }

    private suspend fun loadNameAnimeList(): List<AnimeInfo> {
        val apiResponse = appNetworkClient.getNameCategoryList(0).body<Response>()

        val anime = apiResponse.results.map { it.mapToNameCategoryModel() }

        appDatabase.nameCategoryDao().insertAll(anime)

        return apiResponse.results.map { it.toAnimeInfo() }
    }

    private suspend fun loadFilmAnimeList(): List<AnimeInfo> {
        val apiResponse = appNetworkClient.getFilmCategoryList(0).body<Response>()

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
                    val response = appNetworkClient
                            .searchAnimeById(animeId, user.id.toInt())
                    if (response.status.isSuccess()) {
                        val animeList = response.body<AnimeInfoDto>().toAnimeDetailInfo()
                        user.copy(lastWatchedAnime = animeList.toLastWatched())
                    } else {
                        user
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