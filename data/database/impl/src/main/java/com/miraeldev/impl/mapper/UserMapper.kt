package com.miraeldev.impl.mapper

import com.miraeldev.impl.models.user.toModel
import com.miraeldev.models.dto.UserDto
import com.miraeldev.models.user.User
import tables.UserDbModel

fun UserDbModel.toModel(): User {
    return User(
        id = this.id,
        username = this.username,
        name = this.name ?: "",
        image = this.image ?: "",
        password = "",
        email = this.email ?: "",
        lastWatchedAnime = this.lastWatchedAnimeDbModel?.toModel()
    )
}
fun UserDto.toDbModel(): UserDbModel {
    return UserDbModel(
        id = this.id,
        username = this.username,
        name = this.name ?: "",
        image = this.image ?: "",
        email = this.email ?: "",
        lastWatchedAnimeDbModel = null
    )
}