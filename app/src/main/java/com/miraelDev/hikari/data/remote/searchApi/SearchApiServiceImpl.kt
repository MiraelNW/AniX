package com.miraelDev.hikari.data.remote.searchApi

import android.content.Context
import android.util.Log
import com.miraelDev.hikari.data.remote.ApiRoutes
import com.miraelDev.hikari.data.remote.NetworkHandler
import com.miraelDev.hikari.data.remote.dto.AnimeInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.get
import javax.inject.Inject

class SearchApiServiceImpl @Inject constructor(
        private val client: HttpClient,
        private val networkHandler: NetworkHandler,

) : SearchApiService {

    override suspend fun searchAnimeByName(name: String): ApiResult {

        if (networkHandler.isConnected.value) {
            try {
                return ApiResult.Success(animeList =
                client.get<Array<AnimeInfoDto>>("${ApiRoutes.SEARCH_URL_ANIME_LIST}$name").toList())

            } catch (exception: Exception) {

                return when (exception) {
                    is RedirectResponseException -> {
                        ApiResult.Failure(failureCause = 300)
                    }

                    is ClientRequestException -> {
                        ApiResult.Failure(failureCause = 400)
                    }

                    is ServerResponseException -> {
                        ApiResult.Failure(failureCause = 500)
                    }

                    else -> {
                        ApiResult.Failure(failureCause = 100)
                    }
                }
            }
        } else {
            return ApiResult.Failure(100)
        }

    }

    override suspend fun getAnimeById(id: Int): ApiResult {
        if (networkHandler.isConnected.value) {
            try {
                return ApiResult.Success(animeList =
                client.get<Array<AnimeInfoDto>>("${ApiRoutes.SEARCH_URL_ANIME_ID}$id?format=json").toList())

            } catch (exception: Exception) {

                return when (exception) {
                    is RedirectResponseException -> {
                        ApiResult.Failure(failureCause = 300)
                    }

                    is ClientRequestException -> {
                        ApiResult.Failure(failureCause = 400)
                    }

                    is ServerResponseException -> {
                        ApiResult.Failure(failureCause = 500)
                    }

                    else -> {
                        ApiResult.Failure(failureCause = 100)
                    }
                }
            }
        } else {
            return ApiResult.Failure(100)
        }
    }
}