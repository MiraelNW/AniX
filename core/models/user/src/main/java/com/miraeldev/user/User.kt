package com.miraeldev.user


data class User(
    val username: String = "",
    val userImagePath: String = "",
    val password: String,
    val email: String,
)
