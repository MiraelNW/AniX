package com.miraelDev.hikari.data.remote

import com.miraelDev.hikari.data.remote.searchApi.ApiResult

sealed class FailureCauses {

    object NoInternet : FailureCauses()

    object NotFound : FailureCauses()

    object RedirectResponse : FailureCauses()

    object BadServer : FailureCauses()

    object BadClient : FailureCauses()

    class UnknownProblem(e: Exception) : FailureCauses()

    object NoFail : FailureCauses()

}