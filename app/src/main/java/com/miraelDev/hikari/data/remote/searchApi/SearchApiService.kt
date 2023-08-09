package com.miraelDev.hikari.data.remote.searchApi

interface SearchApiService {

    suspend fun searchAnimeByName(name: String): ApiResult

}