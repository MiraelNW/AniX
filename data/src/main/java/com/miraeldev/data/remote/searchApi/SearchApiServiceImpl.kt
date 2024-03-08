package com.miraeldev.data.remote.searchApi

import com.miraeldev.data.local.AppDatabase
import com.miraeldev.data.network.AppNetworkClient
import com.miraeldev.data.remote.ApiResult
import com.miraeldev.data.remote.NetworkHandler
import com.miraeldev.data.remote.dto.AnimeInfoDto
import com.miraeldev.result.FailureCauses
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.first
import me.tatarka.inject.annotations.Inject

@Inject
class SearchApiServiceImpl(
    private val appNetworkClient: AppNetworkClient,
    private val networkHandler: NetworkHandler,
    private val appDatabase: AppDatabase
) : SearchApiService {

    override suspend fun getAnimeById(id: Int): ApiResult {
        val user = appDatabase.userDao().getUserFlow().first()

        return if (networkHandler.isConnected.value) {
            try {
                ApiResult.Success(
                    animeList = listOf(
                        appNetworkClient
                            .searchAnimeById(id.toString(), user.id.toString())
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