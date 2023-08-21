package com.miraelDev.hikari.data.remote

sealed class FailureCauses {

    object NoInternet : FailureCauses()

    object NotFound : FailureCauses()

    object RedirectResponse : FailureCauses()

    object BadServer : FailureCauses()

    object BadClient : FailureCauses()

    class UnknownProblem(e: Exception) : FailureCauses()

    object NoFail : FailureCauses()

}