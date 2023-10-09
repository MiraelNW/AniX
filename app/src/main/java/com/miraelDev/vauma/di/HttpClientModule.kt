package com.miraelDev.vauma.di

import android.content.Context
import android.util.Log
import com.miraelDev.vauma.data.dataStore.LocalTokenService
import com.miraelDev.vauma.data.remote.NetworkHandler
import com.miraelDev.vauma.domain.models.auth.AccessToken
import com.miraelDev.vauma.domain.models.auth.RefreshToken
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
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    fun provideHttpClient(localTokenService: LocalTokenService): HttpClient {

        return HttpClient(Android).config {

            defaultRequest {
                url("https://vauma.fun/")
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
                        val accessToken = client.post {
                            markAsRefreshTokenRequest()
                            url("auth/token/refresh/")
                            setBody(RefreshToken(refreshToken))
                        }.body<AccessToken>()
                        BearerTokens(
                            accessToken = accessToken.bearerToken,
                            refreshToken = refreshToken
                        )
                    }
                }
            }

            install(HttpRequestRetry) {
                maxRetries = 5
                retryIf { request, response ->
                    !response.status.isSuccess()
                }
                delayMillis { retry ->
                    retry * 2000L
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