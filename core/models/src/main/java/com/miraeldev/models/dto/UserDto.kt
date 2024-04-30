package com.miraeldev.models.dto

import com.miraeldev.models.user.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    val username: String,
    val name: String?,
    val image: String?,
    val email: String?,
)

fun UserDto.toModel(): User {
    return User(
        id = this.id,
        username = this.username,
        name = this.name ?: "",
        image = this.image ?: "",
        password = "",
        email = this.email ?: "",
        lastWatchedAnime = null
    )
}
