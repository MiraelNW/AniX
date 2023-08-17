package com.miraelDev.hikari.data.local.Dao


import com.miraelDev.hikari.data.local.models.AnimeInfoDbModel
import kotlinx.coroutines.flow.Flow


interface FavouriteAnimeDao {
    fun getFavouriteAnimeList(): List<AnimeInfoDbModel>

    suspend fun insertFavouriteAnimeItem(animeItem: AnimeInfoDbModel)

    suspend fun deleteFavouriteAnimeItem(animeItemId: Int)

    suspend fun searchAnimeItem(name : String):List<AnimeInfoDbModel>
}