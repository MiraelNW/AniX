package com.miraeldev.data.mapper

import com.miraeldev.api.models.LocalUserEmailDataModel
import com.miraeldev.user.UserEmail
import me.tatarka.inject.annotations.Inject

@Inject
class UserModelsMapper{

    fun mapLocalUserToDataModel(user: UserEmail): LocalUserEmailDataModel {
        return LocalUserEmailDataModel(user.email)
    }
}