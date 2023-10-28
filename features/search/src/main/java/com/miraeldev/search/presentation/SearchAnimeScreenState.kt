package com.miraeldev.search.presentation

import androidx.paging.PagingData
import com.miraeldev.anime.AnimeInfo
import kotlinx.coroutines.flow.Flow

sealed class SearchAnimeScreenState {

    data object EmptyList : SearchAnimeScreenState()


    data class InitialList(
        val result: Flow<PagingData<AnimeInfo>>
    ) : SearchAnimeScreenState()

    data object SearchHistory : SearchAnimeScreenState()

    data class SearchResult(
        val result: Flow<PagingData<AnimeInfo>>
    ) : SearchAnimeScreenState()

}
