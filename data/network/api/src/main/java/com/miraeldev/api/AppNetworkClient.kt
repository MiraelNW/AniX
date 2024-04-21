package com.miraeldev.api

import com.miraeldev.anime.AnimeInfo
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse

interface AppNetworkClient {

    val client: HttpClient
    suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo): HttpResponse
    suspend fun getNewCategoryList(page: Long): HttpResponse
    suspend fun getPopularCategoryList(page: Long): HttpResponse
    suspend fun getNameCategoryList(page: Long): HttpResponse
    suspend fun getFilmCategoryList(page: Long): HttpResponse
    suspend fun getInitialListCategoryList(page: Long): HttpResponse
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
    suspend fun searchAnimeById(id: Int,userId: Int): HttpResponse
}