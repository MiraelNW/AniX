package com.miraelDev.hikari.presentation.animeInfoDetailAndPlay

import com.miraelDev.hikari.data.remote.FailureCauses
import com.miraelDev.hikari.domain.models.AnimeInfo

sealed class AnimeDetailScreenState {

    object Loading : AnimeDetailScreenState()

    object Initial : AnimeDetailScreenState()

    data class SearchFailure(val failure: FailureCauses) : AnimeDetailScreenState()

    data class SearchResult(val result: List<AnimeInfo>) : AnimeDetailScreenState()

}
