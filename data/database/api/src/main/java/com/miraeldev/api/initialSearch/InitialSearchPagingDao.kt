
package com.miraeldev.api.initialSearch

import com.miraeldev.models.paging.LastDbNode
import com.miraeldev.models.paging.PagingAnimeInfo

interface InitialSearchPagingDao {
    suspend fun insertAll(
        anime: List<PagingAnimeInfo>,
        page: Long,
        isLast: Boolean,
        insertTime: Long
    )

    fun getLastNode() : LastDbNode

    fun getAnimeByPage(page: Long): List<PagingAnimeInfo>

    suspend fun clearAllAnime()
}