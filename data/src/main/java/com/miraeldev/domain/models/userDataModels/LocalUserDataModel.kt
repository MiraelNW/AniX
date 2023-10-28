package com.miraeldev.domain.models.userDataModels

import com.miraeldev.user.LocalUser
import kotlinx.serialization.Serializable

@Serializable
data class LocalUserDataModel(
    val email: String,
)

fun LocalUserDataModel.toLocalUser(): LocalUser {
    return LocalUser(this.email)
}