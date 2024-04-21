package com.miraeldev.api


import com.miraeldev.models.user.User
import kotlinx.coroutines.flow.Flow

interface UserDao {

    fun getUserFlow(): Flow<User>

    suspend fun getUser(): User?

    suspend fun insertUser(user: User)

    suspend fun deleteOldUser()

}