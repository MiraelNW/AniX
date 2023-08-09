package com.miraelDev.hikari.presentation.SearchAimeScreen

import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.result.Result

sealed class SearchAnimeScreenState {

    object Loading : SearchAnimeScreenState()

    object Initial : SearchAnimeScreenState()

    data class SearchFailure(val failure: Int) : SearchAnimeScreenState()

    data class SearchResult(val result: List<AnimeInfo>) : SearchAnimeScreenState()

}
