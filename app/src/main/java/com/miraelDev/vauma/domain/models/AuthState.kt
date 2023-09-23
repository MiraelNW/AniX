package com.miraelDev.vauma.domain.models

sealed class AuthState {

    object Authorized: AuthState()

    object NotAuthorized: AuthState()

    object Initial: AuthState()
}
