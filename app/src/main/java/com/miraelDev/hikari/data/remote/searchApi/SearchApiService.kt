package com.miraelDev.hikari.data.remote.searchApi

import com.miraelDev.hikari.data.remote.dto.AnimeInfoDto

interface SearchApiService {

    suspend fun searchAnimeByName(name: String): List<AnimeInfoDto>

}