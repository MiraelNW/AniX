
package com.miraeldev.data.local.dao.initialSearch

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.models.initialSearch.PagingInitialSearchAnimeInfoDbModel

@Dao
internal interface InitialSearchPagingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<PagingInitialSearchAnimeInfoDbModel>)

    @Query("Select * From paging_initial_search_anime order By page, score desc")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Delete From paging_initial_search_anime")
    suspend fun clearAllAnime()

}