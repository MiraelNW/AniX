package com.miraelDev.hikari.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.Json
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.ANDROID
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json
import okhttp3.internal.ignoreIoExceptions
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {


    private fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true; prettyPrint = true; }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {

        return HttpClient(Android) {

            install(JsonFeature) {
                serializer = KotlinxSerializer(createJson())
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 10000L
                connectTimeoutMillis = 10000L
                socketTimeoutMillis = 10000L
            }

        }
    }

}