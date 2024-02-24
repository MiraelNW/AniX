package com.miraeldev.data.remote

import com.miraeldev.data.remote.dto.AnimeInfoDto
import com.miraeldev.result.FailureCauses

sealed class ApiResult {

    class Success(val animeList: List<AnimeInfoDto>) : ApiResult()

    class Failure(val failureCause: FailureCauses) : ApiResult()
}