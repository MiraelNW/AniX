package com.miraeldev.forgotpassword.domain.models

data class EmailChooseErrorModel(
    val emailNotExistError: Boolean = false,
    val emailNotValidError: Boolean = false
)
