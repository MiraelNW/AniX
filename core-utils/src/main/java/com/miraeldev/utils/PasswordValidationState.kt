package com.miraeldev.utils

data class PasswordValidationState(
    val hasMinimum: Boolean = true,
    val hasCapitalizedLetter: Boolean = true,
    val successful: Boolean = true
)