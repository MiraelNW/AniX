package com.miraelDev.hikari.data.remote.searchApi

import com.miraelDev.hikari.data.remote.FailureCauses
import com.miraelDev.hikari.data.remote.dto.AnimeInfoDto

sealed class ApiResult {

    class Success(val animeList: List<AnimeInfoDto>) : ApiResult()

    class Failure(val failureCause: FailureCauses) : ApiResult()
}