package com.miraelDev.vauma.domain.result

import com.miraelDev.vauma.data.remote.FailureCauses

sealed class Result<out T:Any> {

    data class  Success <out T:Any> (val  animeList: List<T>) : Result<T>()

    data class Failure(val failureCause: FailureCauses) : Result<Nothing>()

    object Initial : Result<Nothing>()

}