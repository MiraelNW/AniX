package com.miraeldev.account.domain

import androidx.compose.runtime.Stable
import com.miraeldev.models.user.User

@Stable
data class UserModel(
    val id: Long = -1,
    val username: String = "",
    val name: String = "",
    val image: String = "",
    val email: String = "",
)

fun User.toUserModel(): UserModel {
    return UserModel(
        id = this.id,
        username = this.username,
        name = this.name,
        image = this.image,
        email = this.email
    )
}