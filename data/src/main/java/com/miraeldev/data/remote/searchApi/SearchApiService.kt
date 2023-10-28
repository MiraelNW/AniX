package com.miraeldev.data.remote.searchApi

import com.miraeldev.data.remote.ApiResult

internal interface SearchApiService {

    suspend fun getAnimeById(id:Int): ApiResult

}