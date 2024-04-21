package com.miraeldev.local.dao.popularCategory.api

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto
import com.miraeldev.models.paging.LastDbNode
import com.miraeldev.models.paging.PagingAnimeInfo

interface PopularCategoryDao {

    suspend fun insertAll(anime: List<AnimeInfoDto>)

    suspend fun getAnimeList(): List<AnimeInfo>

    suspend fun isEmpty(): Boolean
}