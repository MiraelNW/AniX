package com.miraeldev.account.data

import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun logOutUser()

    suspend fun getUserEmail(): String

    suspend fun setPreference(key:String,value:Boolean)

    suspend fun  getPreference(key:String) : Boolean
}