package com.miraeldev.impl.repository

import com.miraeldev.api.AppNetworkClient
import com.miraeldev.api.HomeDataRepository
import com.miraeldev.api.UserDataRepository
import com.miraeldev.api.filmCategory.FilmCategoryDao
import com.miraeldev.api.nameCategory.NameCategoryDao
import com.miraeldev.api.newCategory.NewCategoryDao
import com.miraeldev.api.popularCategory.PopularCategoryDao
import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto
import com.miraeldev.models.dto.Response
import com.miraeldev.models.dto.toAnimeDetailInfo
import com.miraeldev.models.dto.toAnimeInfo
import com.miraeldev.models.dto.toLastWatched
import com.miraeldev.models.user.User
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
    private val userDataRepository: UserDataRepository,
    private val newCategoryDao: NewCategoryDao,
    private val popularCategoryDao: PopularCategoryDao,
    private val nameCategoryDao: NameCategoryDao,
    private val filmCategoryDao: FilmCategoryDao,
    private val systemCurrentTime: Long = System.currentTimeMillis()
) : HomeDataRepository {
    override suspend fun loadData(): Map<Int, List<AnimeInfo>> {
        val isNewEmpty = newCategoryDao.isEmpty()
        val isPopularEmpty = popularCategoryDao.isEmpty()
        val isNameEmpty = nameCategoryDao.isEmpty()
        val isFilmEmpty = filmCategoryDao.isEmpty()

        val createTime = newCategoryDao.getCreateTime()
        val anyIsEmpty = isNewEmpty || isPopularEmpty || isNameEmpty || isFilmEmpty

        return when {
            anyIsEmpty || systemCurrentTime - createTime > FIVE_HOURS_IN_MILLIS -> {
                val map = mutableMapOf<Int, List<AnimeInfo>>()
                map[0] = loadNewAnimeList()
                map[1] = loadPopularAnimeList()
                map[2] = loadNameAnimeList()
                map[3] = loadFilmAnimeList()
                map
            }

            else -> getAnimeListForCategory()
        }
    }

    private suspend fun loadNewAnimeList(): List<AnimeInfo> {
        val apiResponse = appNetworkClient.getNewCategoryList(0).body<Response>()

        val anime = apiResponse.results

        newCategoryDao.insertAll(anime)

        return apiResponse.results.map { it.toAnimeInfo() }
    }

    private suspend fun loadPopularAnimeList(): List<AnimeInfo> {
        val apiResponse = appNetworkClient.getPopularCategoryList(0).body<Response>()

        val anime = apiResponse.results

        popularCategoryDao.insertAll(anime)

        return apiResponse.results.map { it.toAnimeInfo() }
    }

    private suspend fun loadNameAnimeList(): List<AnimeInfo> {
        val apiResponse = appNetworkClient.getNameCategoryList(0).body<Response>()

        val anime = apiResponse.results

        nameCategoryDao.insertAll(anime)

        return apiResponse.results.map { it.toAnimeInfo() }
    }

    private suspend fun loadFilmAnimeList(): List<AnimeInfo> {
        val apiResponse = appNetworkClient.getFilmCategoryList(0).body<Response>()

        val anime = apiResponse.results

        filmCategoryDao.insertAll(anime)

        return apiResponse.results.map { it.toAnimeInfo() }
    }

    private suspend fun getAnimeListForCategory(): Map<Int, List<AnimeInfo>> {

        val newAnimeList = newCategoryDao.getAnimeList()
        val popularAnimeList = popularCategoryDao.getAnimeList()
        val nameAnimeList = nameCategoryDao.getAnimeList()
        val filmsAnimeList = filmCategoryDao.getAnimeList()

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
                    val response = appNetworkClient.searchAnimeById(-1, user.id.toInt())
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