package com.miraelDev.vauma.presentation.searchAimeScreen

import androidx.paging.PagingData
import com.miraelDev.vauma.domain.models.AnimeInfo
import kotlinx.coroutines.flow.Flow

sealed class SearchAnimeScreenState {

    object EmptyList : SearchAnimeScreenState()


    data class InitialList(
        val result: Flow<PagingData<AnimeInfo>>
    ) : SearchAnimeScreenState()

    object SearchHistory : SearchAnimeScreenState()

    data class SearchResult(
        val result: Flow<PagingData<AnimeInfo>>
    ) : SearchAnimeScreenState()

}
