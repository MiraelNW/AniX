package com.miraeldev.signin.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordErrorModel(
    val networkError: Boolean = false,
    val passwordError: Boolean = false,
    val repeatedPasswordError: Boolean = false,
    val passwordLengthError: Boolean = false,
    val passwordHasCapitalizedLetterError: Boolean = false
)
