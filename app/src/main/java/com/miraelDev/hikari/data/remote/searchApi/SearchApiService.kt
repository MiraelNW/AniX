package com.miraelDev.hikari.data.remote.searchApi

import com.miraelDev.hikari.data.remote.ApiResult

interface SearchApiService {

    suspend fun searchAnimeByName(name: String): ApiResult

    suspend fun getAnimeById(id:Int): ApiResult

}