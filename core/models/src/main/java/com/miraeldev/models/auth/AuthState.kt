package com.miraeldev.models.auth

sealed class AuthState {

    data object Authorized: AuthState()

    data object NotAuthorized: AuthState()

    data object Initial: AuthState()
}
