package com.miraeldev.models.models.userDataModels

import com.miraeldev.dataStore.models.LocalUserEmailDataModel
import com.miraeldev.user.UserEmail
import kotlinx.serialization.Serializable


fun LocalUserEmailDataModel.toLocalUserEmail(): UserEmail {
    return UserEmail(this.email)
}