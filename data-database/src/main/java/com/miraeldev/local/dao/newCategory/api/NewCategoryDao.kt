package com.miraeldev.local.dao.newCategory.api

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto
import tables.newcategory.NewCategoryAnimeInfoDbModel

interface NewCategoryDao {

    suspend fun insertAll(anime: List<AnimeInfoDto>)

    suspend fun getAnimeList(): List<AnimeInfo>

    suspend fun isEmpty(): Boolean

    suspend fun getCreateTime(): Long

}