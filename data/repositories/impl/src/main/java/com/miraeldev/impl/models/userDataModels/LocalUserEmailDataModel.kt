package com.miraeldev.impl.models.userDataModels

import com.miraeldev.api.models.LocalUserEmailDataModel
import com.miraeldev.user.UserEmail

fun LocalUserEmailDataModel.toLocalUserEmail(): UserEmail {
    return UserEmail(this.email)
}