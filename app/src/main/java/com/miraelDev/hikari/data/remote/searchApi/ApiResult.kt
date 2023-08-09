package com.miraelDev.hikari.data.remote.searchApi

import com.miraelDev.hikari.data.remote.dto.AnimeInfoDto

sealed class ApiResult(animeList: List<AnimeInfoDto> = emptyList(), failureCause: Int = -1) {

    class Success(val animeList: List<AnimeInfoDto>) : ApiResult(animeList = animeList)

    class Failure(val failureCause: Int) : ApiResult(failureCause = failureCause)
}