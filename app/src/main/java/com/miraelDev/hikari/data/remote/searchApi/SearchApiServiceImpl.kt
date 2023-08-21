package com.miraelDev.hikari.data.remote.searchApi

import com.miraelDev.hikari.data.remote.ApiResult
import com.miraelDev.hikari.data.remote.ApiRoutes
import com.miraelDev.hikari.data.remote.FailureCauses
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
        private val networkHandler: NetworkHandler
) : SearchApiService {

    override suspend fun searchAnimeByName(name: String): ApiResult {

        if (networkHandler.isConnected.value) {
            return try {
                val resultList = client
                        .get<Array<AnimeInfoDto>>("${ApiRoutes.SEARCH_URL_ANIME_LIST}$name")
                        .toList()
                if (resultList.isEmpty()) {
                    ApiResult.Failure(failureCause = FailureCauses.NotFound)
                } else {
                    ApiResult.Success(animeList = resultList)
                }

            } catch (exception: Exception) {
                exceptionHandler(exception)
            }
        } else {
            return ApiResult.Failure(failureCause = FailureCauses.NoInternet)
        }

    }

    override suspend fun getAnimeById(id: Int): ApiResult {
        if (networkHandler.isConnected.value) {
            return try {
                ApiResult.Success(animeList =
                client.get<Array<AnimeInfoDto>>("${ApiRoutes.SEARCH_URL_ANIME_ID}$id?format=json").toList())

            } catch (exception: Exception) {

                exceptionHandler(exception)
            }
        } else {
            return ApiResult.Failure(FailureCauses.NoInternet)
        }
    }

    private fun exceptionHandler(exception: Exception): ApiResult {
        return when (exception) {
            is RedirectResponseException -> {
                ApiResult.Failure(failureCause = FailureCauses.RedirectResponse)
            }

            is ClientRequestException -> {
                ApiResult.Failure(failureCause = FailureCauses.BadClient)
            }

            is ServerResponseException -> {
                ApiResult.Failure(failureCause = FailureCauses.BadServer)
            }

            else -> {
                ApiResult.Failure(failureCause = FailureCauses.UnknownProblem(exception))
            }
        }
    }
}