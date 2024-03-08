package com.miraeldev.data.network

import com.miraeldev.anime.AnimeInfo
import io.ktor.client.statement.HttpResponse

interface AppNetworkClient {
    suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo): HttpResponse

    suspend fun getNewCategoryList(page: Int): HttpResponse
    suspend fun getPopularCategoryList(page: Int): HttpResponse
    suspend fun getNameCategoryList(page: Int): HttpResponse
    suspend fun getFilmCategoryList(page: Int): HttpResponse
    suspend fun getInitialListCategoryList(page: Int): HttpResponse
    suspend fun getPagingFilteredList(
        name: String,
        yearCode: String?,
        sortCode: String?,
        genreCode: String,
        page: Int,
        pageSize: Int
    ): HttpResponse

    suspend fun saveRemoteUser(): HttpResponse
    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        repeatedPassword: String
    ): HttpResponse
    suspend fun searchAnimeById(id: String,userId: String): HttpResponse
}