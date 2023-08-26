package com.miraelDev.hikari.di

import com.miraelDev.hikari.data.remote.NetworkHandler
import com.miraelDev.hikari.data.remote.categoriesLists.FilmsCategoryPagingDataStore
import com.miraelDev.hikari.data.remote.categoriesLists.NameCategoryPagingDataStore
import com.miraelDev.hikari.data.remote.categoriesLists.NewCategoryPagingDataStore
import com.miraelDev.hikari.data.remote.categoriesLists.PopularCategoryPagingDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagingModule {

    @Provides
    @Singleton
    fun provideNewCategoryPagingDataStore(client: HttpClient, networkHandler: NetworkHandler): NewCategoryPagingDataStore {
        return NewCategoryPagingDataStore(client, networkHandler)
    }
    @Provides
    @Singleton
    fun providePopularCategoryPagingDataStore(client: HttpClient, networkHandler: NetworkHandler): PopularCategoryPagingDataStore {
        return PopularCategoryPagingDataStore(client, networkHandler)
    }
    @Provides
    @Singleton
    fun provideFilmsCategoryPagingDataStore(client: HttpClient, networkHandler: NetworkHandler): FilmsCategoryPagingDataStore {
        return FilmsCategoryPagingDataStore(client, networkHandler)
    }
    @Provides
    @Singleton
    fun provideNameCategoryPagingDataStore(client: HttpClient, networkHandler: NetworkHandler): NameCategoryPagingDataStore {
        return NameCategoryPagingDataStore(client, networkHandler)
    }


}