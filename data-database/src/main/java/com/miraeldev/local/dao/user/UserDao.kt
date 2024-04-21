package com.miraeldev.local.dao.user


import kotlinx.coroutines.flow.Flow
import tables.UserDbModel

interface UserDao {


    fun getUserFlow(): Flow<UserDbModel>

    suspend fun getUser(): UserDbModel?

    suspend fun insertUser(user: UserDbModel)

    suspend fun deleteOldUser()

}