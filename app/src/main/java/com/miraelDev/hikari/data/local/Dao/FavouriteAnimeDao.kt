package com.miraelDev.hikari.data.local.Dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraelDev.hikari.data.local.models.AnimeInfoDbModel
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteAnimeDao {

    @Query("SELECT * FROM favList")
    suspend fun getFavouriteAnimeList(): List<AnimeInfoDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteAnimeItem(animeItem: AnimeInfoDbModel)

    @Query("DELETE FROM favList WHERE id= :animeItemId")
    suspend fun deleteFavouriteAnimeItem(animeItemId: Int)

    @Query("SELECT * FROM favList WHERE nameEn LIKE ('%' || :name || '%') OR nameRu LIKE ('%' || :name || '%') ")
    suspend fun searchAnimeItem(name : String):List<AnimeInfoDbModel>
}