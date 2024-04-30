package com.miraeldev.api

import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.user.User
import kotlinx.coroutines.flow.Flow

interface HomeDataRepository {

    suspend fun loadData(): Map<Int, List<AnimeInfo>>

    fun getUserInfo(): Flow<User>
}