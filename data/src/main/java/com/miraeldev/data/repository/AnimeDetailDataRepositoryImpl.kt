package com.miraeldev.data.repository

import com.miraeldev.AnimeDetailDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.VideoPlayerDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.local.dao.FavouriteAnimeDao
import com.miraeldev.data.mapper.AnimeModelsMapper
import com.miraeldev.data.remote.ApiResult
import com.miraeldev.data.remote.ApiRoutes
import com.miraeldev.data.remote.dto.FavouriteAnimeSendRequest
import com.miraeldev.data.remote.dto.toAnimeDetailInfo
import com.miraeldev.data.remote.searchApi.SearchApiService
import com.miraeldev.di.qualifiers.CommonHttpClient
import com.miraeldev.result.ResultAnimeDetail
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class AnimeDetailDataRepositoryImpl @Inject constructor(
    private val searchApiService: SearchApiService,
    @CommonHttpClient private val client: HttpClient,
    private val userDataRepository: UserDataRepository,
    private val videoPlayerDataRepository: VideoPlayerDataRepository,
    private val favouriteAnimeDao: FavouriteAnimeDao,
    private val animeModelsMapper: AnimeModelsMapper,
    private val localTokenService: LocalTokenService
) : AnimeDetailDataRepository {

    private val _animeDetail = MutableSharedFlow<ResultAnimeDetail>()

    override fun getAnimeDetail(): SharedFlow<ResultAnimeDetail> = _animeDetail.asSharedFlow()

    override suspend fun loadAnimeDetail(animeId: Int) {

        val token = localTokenService.getBearerToken()

        when (val apiResult = searchApiService.getAnimeById(animeId)) {
            is ApiResult.Success -> {
                val animeList = apiResult.animeList.map { it.toAnimeDetailInfo() }
                videoPlayerDataRepository.loadVideoPlayer(animeList.first())
                _animeDetail.emit(
                    ResultAnimeDetail.Success(animeList = animeList)
                )
            }

            is ApiResult.Failure -> {
                _animeDetail.emit(
                    ResultAnimeDetail.Failure(failureCause = apiResult.failureCause)
                )

            }
        }
    }

    override suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo) {

        val bearerToken = localTokenService.getBearerToken()

        val userInfo = userDataRepository.getUserInfo()

        client.post {
            url(ApiRoutes.SET_ANIME_FAV_STATUS)
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
            setBody(
                FavouriteAnimeSendRequest(
                    animeId = animeInfo.id.toLong(),
                    userId = userInfo.id,
                    isFavourite = isSelected
                )
            )
        }

        if (isSelected) {
            favouriteAnimeDao.insertFavouriteAnimeItem(
                animeModelsMapper.mapAnimeInfoToAnimeDbModel(animeInfo)
            )
        } else {
            favouriteAnimeDao.deleteFavouriteAnimeItem(animeInfo.id)
        }
    }


}