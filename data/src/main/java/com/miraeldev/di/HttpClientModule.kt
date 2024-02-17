package com.miraeldev.di

import android.content.Context
import android.util.Log
import com.miraeldev.LocalUserDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.data.BuildConfig
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.remote.NetworkHandler
import com.miraeldev.di.qualifiers.AuthClient
import com.miraeldev.di.qualifiers.CommonHttpClient
import com.miraeldev.domain.models.auth.AccessTokenDataModel
import com.miraeldev.domain.models.auth.RefreshToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object HttpClientModule {

    @Provides
    @Singleton
    @AuthClient
    fun provideAuthClient(): HttpClient {

        return HttpClient(Android).config {

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

            install(HttpRequestRetry) {
                maxRetries = 1
                retryIf { request, response ->
                    !response.status.isSuccess() && response.status.value != 401
                }
                delayMillis {
                    500L
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
                requestTimeoutMillis = 2000L
                connectTimeoutMillis = 10000L
                socketTimeoutMillis = 5000L
            }

        }
    }

    @Provides
    @Singleton
    @CommonHttpClient
    internal fun provideHttpClient(
        localTokenService: LocalTokenService,
        userDataRepository: LocalUserDataRepository
    ): HttpClient {

        return HttpClient(Android).config {

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

                            userDataRepository.setUserUnAuthorizedStatus()
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
    }

    @Provides
    @Singleton
    fun provideNetworkHandler(@ApplicationContext context: Context): NetworkHandler {
        return NetworkHandler(context)
    }

}