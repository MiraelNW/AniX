package com.miraelDev.vauma.domain.models.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username : String ="",
    val password : String,
    val email : String,
)
