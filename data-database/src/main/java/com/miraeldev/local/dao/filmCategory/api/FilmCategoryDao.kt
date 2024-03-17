package com.miraeldev.local.dao.filmCategory.api

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto


interface FilmCategoryDao {

    suspend fun insertAll(anime: List<AnimeInfoDto>)

    suspend fun getAnimeList(): List<AnimeInfo>

    suspend fun isEmpty(): Boolean

}