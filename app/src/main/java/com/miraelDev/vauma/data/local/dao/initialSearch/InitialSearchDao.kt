
package com.miraelDev.vauma.data.local.dao.initialSearch

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraelDev.vauma.data.local.models.initialSearch.InitialSearchAnimeInfoDbModel
import com.miraelDev.vauma.domain.models.anime.AnimeInfo

@Dao
interface InitialSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<InitialSearchAnimeInfoDbModel>)

    @Query("Select * From initial_search_anime order By page, score desc")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Delete From initial_search_anime")
    suspend fun clearAllAnime()

}