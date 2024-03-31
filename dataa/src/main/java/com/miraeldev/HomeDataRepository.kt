package com.miraeldev

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow

interface HomeDataRepository {

    suspend fun loadData(): Map<Int, List<AnimeInfo>>

    fun getUserInfo():Flow<User>

}