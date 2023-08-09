package com.miraelDev.hikari.data.remote.searchApi

import com.miraelDev.hikari.data.remote.ApiRoutes
import com.miraelDev.hikari.data.remote.dto.AnimeInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.get
import javax.inject.Inject

class SearchApiServiceImpl @Inject constructor(
    private val client: HttpClient
) : com.miraelDev.hikari.data.remote.searchApi.SearchApiService {
    override suspend fun searchAnimeByName(name: String): ApiResult {
        try {
            return ApiResult.Success(animeList =
            client.get<Array<AnimeInfoDto>>("${ApiRoutes.SEARCH_URL}$name").toList())
            //makes the assumption that you have already handled IO, SocketTimeout and UnknownHost exceptions
        } catch (exception: ResponseException) {

           return when (exception) {
                is RedirectResponseException -> {
                    ApiResult.Failure(failureCause = 300)
//                    // Status codes 3XX
//                    val errorString = exception.response.receive<String>()
//
//                    val jsonObject = JSONObject(errorString)
//
//                    val errorMessage = jsonObject.getString("status_message")
//
//                    // pass it wherever you would like to
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
    }

}