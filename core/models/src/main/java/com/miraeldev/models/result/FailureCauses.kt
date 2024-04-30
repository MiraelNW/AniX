package com.miraeldev.models.result

@Suppress("UnusedPrivateMember")
sealed class FailureCauses {

    data object NoInternet : FailureCauses()

    data object NotFound : FailureCauses()

    data object RedirectResponse : FailureCauses()

    data object BadServer : FailureCauses()

    data object BadClient : FailureCauses()

    class UnknownProblem(e: Exception) : FailureCauses()

    data object NoFail : FailureCauses()
}