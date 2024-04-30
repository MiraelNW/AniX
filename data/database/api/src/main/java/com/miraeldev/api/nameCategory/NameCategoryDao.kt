package com.miraeldev.api.nameCategory

import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto

interface NameCategoryDao {

    suspend fun insertAll(anime: List<AnimeInfoDto>)

    suspend fun getAnimeList(): List<AnimeInfo>

    suspend fun isEmpty(): Boolean
}