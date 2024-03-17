package com.miraeldev.local.dao.nameCategory.api

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto

interface NameCategoryDao {

    suspend fun insertAll(anime: List<AnimeInfoDto>)

    suspend fun getAnimeList(): List<AnimeInfo>

    suspend fun isEmpty(): Boolean

}