package com.miraeldev.data.network

import android.util.Log
import com.miraeldev.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal object AuthNetworkClient {

    fun createClient() = HttpClient(Android).config {

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