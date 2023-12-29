package com.miraeldev.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.data.local.models.favourite.AnimeInfoDbModel
import kotlinx.coroutines.flow.Flow


@Dao
internal interface FavouriteAnimeDao {

    @Query("SELECT * FROM favList LIMIT 10 OFFSET :offset")
    fun getFavouriteAnimeList(offset: Int = 0): Flow<List<AnimeInfoDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteAnimeItem(animeItem: AnimeInfoDbModel)

    @Query("DELETE FROM favList WHERE id= :animeItemId")
    suspend fun deleteFavouriteAnimeItem(animeItemId: Int)

    @Query("SELECT * FROM favList WHERE nameEn LIKE ('%' || :name || '%') OR nameRu LIKE ('%' || :name || '%') ")
    suspend fun searchAnimeItem(name: String): List<AnimeInfoDbModel>
}