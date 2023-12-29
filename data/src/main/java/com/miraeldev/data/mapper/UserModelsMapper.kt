package com.miraeldev.data.mapper

import com.miraeldev.domain.models.userDataModels.LocalUserEmailDataModel
import com.miraeldev.user.UserEmail
import javax.inject.Inject

class UserModelsMapper @Inject constructor() {

    fun mapLocalUserToDataModel(user: UserEmail): LocalUserEmailDataModel {
        return LocalUserEmailDataModel(user.email)
    }
}