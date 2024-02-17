package com.miraeldev.account.domain.model

import com.miraeldev.utils.PasswordValidationState

data class EditProfileErrorModel(
    val passwordNetworkError: Boolean = false,
    val userInfoNetworkError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: PasswordValidationState = PasswordValidationState(),
    val repeatedPasswordError: Boolean = false
)
