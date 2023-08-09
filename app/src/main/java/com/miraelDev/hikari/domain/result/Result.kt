package com.miraelDev.hikari.domain.result

import com.miraelDev.hikari.domain.models.AnimeInfo

sealed class Result(animeList: List<AnimeInfo> = emptyList(), failureCause: Int = -1) {

    data class Success(val animeList: List<AnimeInfo>) : Result(animeList = animeList)

    data class Failure(val failureCause: Int) : Result(failureCause = failureCause)

    object Initial : Result()

}