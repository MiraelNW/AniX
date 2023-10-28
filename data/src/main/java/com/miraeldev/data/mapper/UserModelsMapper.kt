package com.miraeldev.data.mapper

import com.miraeldev.domain.models.userDataModels.LocalUserDataModel
import com.miraeldev.user.LocalUser
import javax.inject.Inject

class UserModelsMapper @Inject constructor() {

    fun mapLocalUserToDataModel(user: LocalUser): LocalUserDataModel {
        return LocalUserDataModel(user.email)
    }
}