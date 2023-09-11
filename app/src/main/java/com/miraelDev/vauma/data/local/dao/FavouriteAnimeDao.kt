package com.miraelDev.vauma.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraelDev.vauma.data.local.models.favourite.AnimeInfoDbModel


@Dao
interface FavouriteAnimeDao {

    @Query("SELECT * FROM favList LIMIT 10 OFFSET :offset")
    suspend fun getFavouriteAnimeList(offset:Int=0): List<AnimeInfoDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteAnimeItem(animeItem: AnimeInfoDbModel)

    @Query("DELETE FROM favList WHERE id= :animeItemId")
    suspend fun deleteFavouriteAnimeItem(animeItemId: Int)

    @Query("SELECT * FROM favList WHERE nameEn LIKE ('%' || :name || '%') OR nameRu LIKE ('%' || :name || '%') ")
    suspend fun searchAnimeItem(name : String):List<AnimeInfoDbModel>
}