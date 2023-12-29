package com.miraeldev.data.local.models.user


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miraeldev.user.User


@Entity(tableName = "user")
data class UserDbModel(
    @PrimaryKey
    val id: Long,
    val username: String,
    val name: String?,
    val image: String?,
    val email: String?,
    val lastWatchedAnimeDbModel: LastWatchedAnimeDbModel?
)

fun UserDbModel.toUserModel(): User {
    return User(
        id = this.id,
        username = this.username,
        name = this.name ?: "",
        image = this.image ?: "",
        email = this.email ?: "",
        lastWatchedAnime = this.lastWatchedAnimeDbModel?.toModel()
    )
}
