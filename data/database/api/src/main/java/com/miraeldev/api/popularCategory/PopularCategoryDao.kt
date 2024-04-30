package com.miraeldev.api.popularCategory

import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto

interface PopularCategoryDao {

    suspend fun insertAll(anime: List<AnimeInfoDto>)

    suspend fun getAnimeList(): List<AnimeInfo>

    suspend fun isEmpty(): Boolean
}