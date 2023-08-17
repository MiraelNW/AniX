package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import com.miraelDev.hikari.data.remote.FailureCauses
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.result.Result

sealed class AnimeDetailScreenState {

    object Loading : AnimeDetailScreenState()

    object Initial : AnimeDetailScreenState()

    data class SearchFailure(val failure: FailureCauses) : AnimeDetailScreenState()

    data class SearchResult(val result: List<AnimeInfo>) : AnimeDetailScreenState()

}
