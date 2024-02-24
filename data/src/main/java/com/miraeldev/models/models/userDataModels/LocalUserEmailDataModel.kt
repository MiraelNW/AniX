package com.miraeldev.models.models.userDataModels

import com.miraeldev.user.UserEmail
import kotlinx.serialization.Serializable

@Serializable
data class LocalUserEmailDataModel(
    val email: String,
)

fun LocalUserEmailDataModel.toLocalUserEmail(): UserEmail {
    return UserEmail(this.email)
}