package com.miraelDev.hikari.presentation.SearchAimeScreen

import androidx.paging.PagingData
import com.miraelDev.hikari.domain.models.AnimeInfo
import kotlinx.coroutines.flow.Flow

sealed class SearchAnimeScreenState {

    object EmptyList : SearchAnimeScreenState()

    data class InitialList(
        val result: Flow<PagingData<AnimeInfo>>,
        val filterList: List<String> = emptyList()
    ) : SearchAnimeScreenState()

    data class SearchHistory(
        val filterList: List<String> = emptyList()
    ) : SearchAnimeScreenState()

    data class SearchResult(
        val result: Flow<PagingData<AnimeInfo>>,
        val filterList: List<String> = emptyList()
    ) : SearchAnimeScreenState()

}
