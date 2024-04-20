package com.miraeldev.impl

import android.util.Log
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.api.AppNetworkClient
import com.miraeldev.api.PreferenceClient
import com.miraeldev.api.UserAuthRepository
import com.miraeldev.impl.models.AccessTokenDataModel
import com.miraeldev.impl.models.FavouriteAnimeSendRequest
import com.miraeldev.impl.models.RefreshToken
import com.miraeldev.local.AppDatabase
import com.miraeldev.local.models.user.toUserModel
import com.miraeldev.impl.models.routes.AppNetworkRoutes
import com.miraeldev.impl.models.routes.AuthNetworkRoutes
import com.miraeldev.user.User
import com.pluto.plugins.network.ktor.PlutoKtorInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
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
    private val preferenceClient: PreferenceClient,
    private val userAuthRepository: UserAuthRepository,
    private val appDatabase: AppDatabase
) : AppNetworkClient {

    override val client: HttpClient = HttpClient(CIO).config {

        defaultRequest {
            url(AppNetworkRoutes.BASE_URL)
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
                    val refreshToken = preferenceClient.getRefreshToken()
                    val bearerToken = preferenceClient.getBearerToken()
                    val accessTokenDataModel = try {
                        client.post {
                            markAsRefreshTokenRequest()
                            url(AuthNetworkRoutes.AUTH_REFRESH_URL)
                            headers {
                                append(HttpHeaders.Authorization, "Bearer $bearerToken")
                            }
                            setBody(RefreshToken(refreshToken))
                        }.body<AccessTokenDataModel>()
                    } catch (e: Exception) {
                        client.post {
                            url(AuthNetworkRoutes.AUTH_LOGOUT_URL)
                            headers {
                                append(HttpHeaders.Authorization, "Bearer $refreshToken")
                            }
                        }
                        if (response.status.isSuccess()) {
                            userAuthRepository.setUserUnAuthorizedStatus()
                            AccessTokenDataModel(bearerToken = "", refreshToken = "")
                        } else {
                            AccessTokenDataModel(
                                bearerToken = bearerToken,
                                refreshToken = refreshToken
                            )
                        }
                    }

                    BearerTokens(
                        accessToken = accessTokenDataModel.bearerToken,
                        refreshToken = accessTokenDataModel.refreshToken
                    )
                }
                loadTokens {
                    val refreshToken = preferenceClient.getRefreshToken()
                    val bearerToken = preferenceClient.getBearerToken()
                    BearerTokens(
                        accessToken = bearerToken,
                        refreshToken = refreshToken
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

//        install(PlutoKtorInterceptor)

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
        val user = appDatabase.userDao().getUser()?.toUserModel() ?: User()
        return client.post {
            url(AppNetworkRoutes.SET_ANIME_FAV_STATUS)
            setBody(
                FavouriteAnimeSendRequest(
                    animeId = animeInfo.id.toLong(),
                    userId = user.id,
                    isFavourite = isSelected
                )
            )
        }
    }

    override suspend fun getNewCategoryList(page: Int): HttpResponse = client.get {
        url("${AppNetworkRoutes.GET_NEW_CATEGORY_LIST_ROUTE}page=$page&page_size=$PAGE_SIZE")
    }

    override suspend fun getPopularCategoryList(page: Int): HttpResponse = client.get {
        url("${AppNetworkRoutes.GET_POPULAR_CATEGORY_LIST_ROUTE}page=$page&page_size=$PAGE_SIZE")
    }

    override suspend fun getNameCategoryList(page: Int): HttpResponse = client.get {
        url("${AppNetworkRoutes.GET_NAME_CATEGORY_LIST_ROUTE}page=$page&page_size=$PAGE_SIZE")
    }

    override suspend fun getFilmCategoryList(page: Int): HttpResponse = client.get {
        url("${AppNetworkRoutes.GET_FILMS_CATEGORY_LIST_ROUTE}page=$page&page_size=$PAGE_SIZE")
    }

    override suspend fun getInitialListCategoryList(page: Long): HttpResponse = client.get {
        url("${AppNetworkRoutes.SEARCH_URL_ANIME_LIST_ROUTE}&page=$page&page_size=$PAGE_SIZE")
    }

    override suspend fun getPagingFilteredList(
        name: String,
        yearCode: String?,
        sortCode: String?,
        genreCode: String,
        page: Int,
        pageSize: Int
    ): HttpResponse = when {
        yearCode != null && sortCode != null && genreCode.isNotEmpty() ->
            client.get {
                url("${AppNetworkRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&sort=$sortCode&date=$yearCode&genres=$genreCode&page=$page&page_size=$pageSize")
            }
        yearCode != null && sortCode != null && genreCode.isEmpty() ->
            client.get {
                url("${AppNetworkRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&sort=$sortCode&date=$yearCode&page=$page&page_size=$pageSize")
            }
        yearCode != null && sortCode == null && genreCode.isNotEmpty() ->
            client.get {
                url("${AppNetworkRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&date=$yearCode&genres=$genreCode&page=$page&page_size=$pageSize")
            }
        yearCode == null && sortCode != null && genreCode.isNotEmpty() ->
            client.get {
                url("${AppNetworkRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&sort=$sortCode&genres=$genreCode&page=$page&page_size=$pageSize")
            }
        yearCode != null && sortCode == null && genreCode.isEmpty() ->
            client.get {
                url("${AppNetworkRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&date=$yearCode&page=$page&page_size=$pageSize")
            }
        yearCode == null && sortCode != null && genreCode.isEmpty() ->
            client.get {
                url("${AppNetworkRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&sort=$sortCode&page=$page&page_size=$pageSize")
            }
        yearCode == null && sortCode == null && genreCode.isNotEmpty() ->
            client.get {
                url("${AppNetworkRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&genres=$genreCode&page=$page&page_size=$pageSize")
            }
        else ->
            client.get {
                url("${AppNetworkRoutes.SEARCH_URL_ANIME_LIST_ROUTE}name=${name}&page=$page&page_size=$pageSize")
            }
    }

    override suspend fun saveRemoteUser(): HttpResponse = client.get {
        url(AppNetworkRoutes.GET_USER_INFO)
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        repeatedPassword: String
    ): HttpResponse = client.post {
        url(AppNetworkRoutes.CHANGE_PASSWORD)
        setBody(
            mapOf(
                Pair("current_password", currentPassword),
                Pair("new_password", newPassword),
                Pair("repeated_password", repeatedPassword)
            )
        )
    }

    override suspend fun searchAnimeById(id: Int, userId: Int): HttpResponse = client.get {
        url("${AppNetworkRoutes.SEARCH_URL_ANIME_ID_ROUTE}?anime_id=$id&user_id=${userId}")
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}