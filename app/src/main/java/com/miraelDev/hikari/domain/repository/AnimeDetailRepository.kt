package com.miraelDev.hikari.domain.repository

import com.miraelDev.hikari.domain.models.AnimeInfo
import kotlinx.coroutines.flow.StateFlow

interface AnimeDetailRepository {

    fun getAnimeDetail(id:Int):AnimeInfo

}