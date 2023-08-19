package com.miraelDev.hikari.domain.result

import com.miraelDev.hikari.data.remote.FailureCauses
import com.miraelDev.hikari.domain.models.AnimeInfo

sealed class Result() {

    data class  Success  (val  animeList: List<AnimeInfo>,val searchText:String="") : Result()

    data class Failure(val failureCause: FailureCauses,val searchText:String = "") : Result()

    object Initial : Result()

}