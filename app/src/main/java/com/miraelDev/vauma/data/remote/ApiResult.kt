package com.miraelDev.vauma.data.remote

import com.miraelDev.vauma.data.remote.dto.AnimeInfoDto

sealed class ApiResult {

    class Success(val animeList: List<AnimeInfoDto>) : ApiResult()

    class Failure(val failureCause: FailureCauses) : ApiResult()
}