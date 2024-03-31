package com.miraeldev.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    val username: String,
    val name: String?,
    val image: String?,
    val email: String?,
)
//
//fun UserDto.toUserDbModel(): UserDbModel {
//    return UserDbModel(
//        id = this.id,
//        username = this.username,
//        name = this.name,
//        image = this.image,
//        email = this.email,
//        lastWatchedAnimeDbModel = null
//    )
//}
