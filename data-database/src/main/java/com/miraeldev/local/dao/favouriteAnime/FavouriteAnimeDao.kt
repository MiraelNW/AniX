package com.miraeldev.local.dao.favouriteAnime


import com.miraeldev.anime.AnimeInfo
import kotlinx.coroutines.flow.Flow

interface FavouriteAnimeDao {
    fun getFavouriteAnimeList(offset: Int = 0): Flow<List<AnimeInfo>>

    suspend fun insertFavouriteAnimeItem(animeItem: AnimeInfo)

    suspend fun deleteFavouriteAnimeItem(animeItemId: Int)

    suspend fun searchAnimeItem(name: String): List<AnimeInfo>
}