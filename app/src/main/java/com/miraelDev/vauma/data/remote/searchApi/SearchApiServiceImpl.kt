package com.miraelDev.vauma.data.remote.searchApi

import com.miraelDev.vauma.data.dataStore.tokenService.LocalTokenService
import com.miraelDev.vauma.data.remote.ApiResult
import com.miraelDev.vauma.data.remote.ApiRoutes
import com.miraelDev.vauma.data.remote.FailureCauses
import com.miraelDev.vauma.data.remote.NetworkHandler
import com.miraelDev.vauma.data.remote.dto.AnimeInfoDto
import com.miraelDev.vauma.di.qualifiers.CommonHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import okhttp3.internal.immutableListOf
import javax.inject.Inject

class SearchApiServiceImpl @Inject constructor(
    @CommonHttpClient private val client: HttpClient,
    private val networkHandler: NetworkHandler,
    private val localTokenService: LocalTokenService
) : SearchApiService {

    override suspend fun getAnimeById(id: Int): ApiResult {

        val bearerToken = localTokenService.getBearerToken()

        return if (networkHandler.isConnected.value) {
            try {
                ApiResult.Success(
                    animeList = immutableListOf(
                        client.get {
                            url("${ApiRoutes.SEARCH_URL_ANIME_ID_ROUTE}$id/")
                            headers {
                                append(HttpHeaders.Authorization, "Bearer $bearerToken")
                            }
                        }
                            .body<AnimeInfoDto>()

                    )
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