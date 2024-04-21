package com.miraeldev.api.newCategory

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto

interface NewCategoryDao {

    suspend fun insertAll(anime: List<AnimeInfoDto>)

    suspend fun getAnimeList(): List<AnimeInfo>

    suspend fun isEmpty(): Boolean

    suspend fun getCreateTime(): Long

}