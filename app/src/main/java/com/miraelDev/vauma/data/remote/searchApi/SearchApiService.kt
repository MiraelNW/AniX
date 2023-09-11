package com.miraelDev.vauma.data.remote.searchApi

import com.miraelDev.vauma.data.remote.ApiResult

interface SearchApiService {

    suspend fun getAnimeById(id:Int): ApiResult

}