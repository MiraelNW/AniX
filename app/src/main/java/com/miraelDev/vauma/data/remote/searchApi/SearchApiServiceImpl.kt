package com.miraelDev.vauma.data.remote.searchApi

import com.miraelDev.vauma.data.remote.ApiResult
import com.miraelDev.vauma.data.remote.ApiRoutes
import com.miraelDev.vauma.data.remote.FailureCauses
import com.miraelDev.vauma.data.remote.NetworkHandler
import com.miraelDev.vauma.data.remote.dto.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject

class SearchApiServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val networkHandler: NetworkHandler
) : SearchApiService {

    override suspend fun getAnimeById(id: Int): ApiResult {
        return if (networkHandler.isConnected.value) {
            try {
                ApiResult.Success(
                    animeList = client
                        .get("${ApiRoutes.SEARCH_URL_ANIME_ID}$id/")
                        .body<Response>()
                        .results
                )

            } catch (exception: Exception) {
                exceptionHandler(exception)
            }
        } else {
            ApiResult.Failure(FailureCauses.NoInternet)
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