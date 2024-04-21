package com.miraeldev.api.filmCategory

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto


interface FilmCategoryDao {

    suspend fun insertAll(anime: List<AnimeInfoDto>)

    suspend fun getAnimeList(): List<AnimeInfo>

    suspend fun isEmpty(): Boolean

}