package com.miraeldev.account.data

import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun logOutUser(): Boolean

    suspend fun getUserInfo():User

    suspend fun getUserEmail(): String

    suspend fun changePassword(
        currentPassword:String,
        newPassword:String,
        repeatedPassword:String
    ):Boolean

    suspend fun setPreference(key:String,value:Boolean)

    suspend fun  getPreference(key:String) : Boolean
}