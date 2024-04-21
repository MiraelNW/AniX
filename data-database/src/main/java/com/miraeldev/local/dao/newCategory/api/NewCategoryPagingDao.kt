package com.miraeldev.local.dao.newCategory.api

import com.miraeldev.models.paging.LastDbNode
import com.miraeldev.models.paging.PagingAnimeInfo

interface NewCategoryPagingDao {

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