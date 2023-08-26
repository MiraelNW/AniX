package com.miraelDev.hikari.data.remote.searchApi

import com.miraelDev.hikari.data.remote.ApiResult

interface SearchApiService {

    suspend fun getAnimeById(id:Int): ApiResult

}