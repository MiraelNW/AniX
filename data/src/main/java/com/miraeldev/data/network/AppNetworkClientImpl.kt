package com.miraeldev.data.network

import android.util.Log
import com.miraeldev.LocalUserDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.BuildConfig
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.local.AppDatabase
import com.miraeldev.data.local.models.user.toUserModel
import com.miraeldev.data.remote.ApiRoutes
import com.miraeldev.data.remote.dto.FavouriteAnimeSendRequest
import com.miraeldev.models.models.auth.AccessTokenDataModel
import com.miraeldev.models.models.auth.RefreshToken
import com.miraeldev.user.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject

@Inject
class AppNetworkClientImpl(
    private val localTokenService: LocalTokenService,
    private val localUserDataRepository: LocalUserDataRepository,
    private val appDatabase: AppDatabase
) : AppNetworkClient {

    private val client: HttpClient = HttpClient(Android).config {

        defaultRequest {
            url(BuildConfig.BASE_URL)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }

        install(HttpCache)

        install(Auth) {
            bearer {
                refreshTokens {
                    val refreshToken = localTokenService.getRefreshToken()
                    val bearerToken = localTokenService.getBearerToken()
                    val accessTokenDataModel = try {
                        client.post {
                            markAsRefreshTokenRequest()
                            url(BuildConfig.AUTH_REFRESH_URL)
                            headers {
                                append(HttpHeaders.Authorization, "Bearer $bearerToken")
                            }
                            setBody(RefreshToken(refreshToken))
                        }.body<AccessTokenDataModel>()
                    } catch (e: Exception) {
                        localUserDataRepository.setUserUnAuthorizedStatus()
                        AccessTokenDataModel(bearerToken = "", refreshToken = "")
                    }

                    BearerTokens(
                        accessToken = accessTokenDataModel.bearerToken,
                        refreshToken = accessTokenDataModel.refreshToken
                    )
                }
            }
        }

        install(HttpRequestRetry) {
            maxRetries = 5
            retryIf { request, response ->
                !response.status.isSuccess() && response.status.value != 401
            }
            delayMillis { retry ->
                retry * 1000L
            }
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i("HttpClient", message)
                }
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 10000L
            connectTimeoutMillis = 10000L
            socketTimeoutMillis = 10000L
        }

    }

    override suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo): HttpResponse {

        val bearerToken = localTokenService.getBearerToken()
        val userInfo = appDatabase.userDao().getUser()?.toUserModel() ?: User()
        return client.post {
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
    }

    override suspend fun getNewCategoryList(page: Int): HttpResponse {
        val bearerToken = localTokenService.getBearerToken()
        return client.get {
            url("${ApiRoutes.GET_NEW_CATEGORY_LIST_ROUTE}page=$page&page_size=${PAGE_SIZE}")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
    }

    override suspend fun getPopularCategoryList(page: Int): HttpResponse {
        val bearerToken = localTokenService.getBearerToken()
        return client.get {
            url("${ApiRoutes.GET_POPULAR_CATEGORY_LIST_ROUTE}page=$page&page_size=${PAGE_SIZE}")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
    }

    override suspend fun getNameCategoryList(page: Int): HttpResponse {
        val bearerToken = localTokenService.getBearerToken()
        return client.get {
            url("${ApiRoutes.GET_NAME_CATEGORY_LIST_ROUTE}page=$page&page_size=${PAGE_SIZE}")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
    }

    override suspend fun getFilmCategoryList(page: Int): HttpResponse {
        val bearerToken = localTokenService.getBearerToken()
        return client.get {
            url("${ApiRoutes.GET_FILMS_CATEGORY_LIST_ROUTE}page=$page&page_size=${PAGE_SIZE}")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
    }

    override suspend fun getInitialListCategoryList(page: Int): HttpResponse {
        val bearerToken = localTokenService.getBearerToken()
        return client.get {
            url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}&page=$page&page_size=${PAGE_SIZE}")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
    }

    override suspend fun getPagingFilteredList(
        name: String,
        yearCode: String?,
        sortCode: String?,
        genreCode: String,
        page: Int,
        pageSize: Int
    ): HttpResponse {
        val bearerToken = localTokenService.getBearerToken()
        return if (yearCode != null && sortCode != null && genreCode.isNotEmpty()) {
            client.get {
                url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&sort=$sortCode&date=$yearCode&genres=$genreCode&page=$page&page_size=$pageSize")
                headers {
                    append(HttpHeaders.Authorization, "Bearer $bearerToken")
                }
            }
        } else if (yearCode != null && sortCode != null && genreCode.isEmpty()) {
            client.get {
                url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&sort=$sortCode&date=$yearCode&page=$page&page_size=$pageSize")
                headers {
                    append(HttpHeaders.Authorization, "Bearer $bearerToken")
                }
            }
        } else if (yearCode != null && sortCode == null && genreCode.isNotEmpty()) {
            client.get {
                url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&date=$yearCode&genres=$genreCode&page=$page&page_size=$pageSize")
                headers {
                    append(HttpHeaders.Authorization, "Bearer $bearerToken")
                }
            }
        } else if (yearCode == null && sortCode != null && genreCode.isNotEmpty()) {
            client.get {
                url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&sort=$sortCode&genres=$genreCode&page=$page&page_size=$pageSize")
                headers {
                    append(HttpHeaders.Authorization, "Bearer $bearerToken")
                }
            }
        } else if (yearCode != null && sortCode == null && genreCode.isEmpty()) {
            client.get {
                url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&date=$yearCode&page=$page&page_size=$pageSize")
                headers {
                    append(HttpHeaders.Authorization, "Bearer $bearerToken")
                }
            }
        } else if (yearCode == null && sortCode != null && genreCode.isEmpty()) {
            client.get {
                url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&sort=$sortCode&page=$page&page_size=$pageSize")
                headers {
                    append(HttpHeaders.Authorization, "Bearer $bearerToken")
                }
            }
        } else if (yearCode == null && sortCode == null && genreCode.isNotEmpty()) {
            client.get {
                url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&genres=$genreCode&page=$page&page_size=$pageSize")
                headers {
                    append(HttpHeaders.Authorization, "Bearer $bearerToken")
                }
            }
        } else {
            client.get {
                url("${ApiRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&page=$page&page_size=$pageSize")
                headers {
                    append(HttpHeaders.Authorization, "Bearer $bearerToken")
                }
            }
        }
    }

    override suspend fun saveRemoteUser(): HttpResponse {
        val bearerToken = localTokenService.getBearerToken()
        return client.get {
            url(ApiRoutes.GET_USER_INFO)
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        repeatedPassword: String
    ): HttpResponse {
        val bearerToken = localTokenService.getBearerToken()
        return client.post {
            url(ApiRoutes.CHANGE_PASSWORD)
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
            setBody(
                mapOf(
                    Pair("current_password", currentPassword),
                    Pair("new_password", newPassword),
                    Pair("repeated_password", repeatedPassword)
                )
            )
        }
    }

    override suspend fun searchAnimeById(id: String, userId: String): HttpResponse {
        val bearerToken = localTokenService.getBearerToken()
        return client.get {
            url("${ApiRoutes.SEARCH_URL_ANIME_ID_ROUTE}?anime_id=$id&user_id=${userId}")
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}