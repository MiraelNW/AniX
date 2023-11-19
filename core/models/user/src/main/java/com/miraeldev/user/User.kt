package com.miraeldev.user


import kotlinx.serialization.Serializable


@Serializable
data class User(
    val username: String = "",
    val userImagePath: String = "",
    val password: String ="",
    val email: String="",
)
