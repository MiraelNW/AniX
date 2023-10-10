package com.miraelDev.vauma.domain.models.user

data class PasswordValidationState(
    val hasMinimum: Boolean = true,
    val hasCapitalizedLetter: Boolean = true,
    val successful: Boolean = true
)