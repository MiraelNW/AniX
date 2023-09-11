package com.miraelDev.vauma.di

import android.content.Context
import com.miraelDev.vauma.data.remote.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.cache.HttpCache
import io.ktor.client.features.json.JsonFeature
import kotlinx.serialization.json.Json
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.ANDROID
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
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

            install(HttpCache)

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

    @Provides
    @Singleton
    fun provideNetworkHandler(@ApplicationContext context: Context): NetworkHandler {
        return NetworkHandler(context)
    }

}