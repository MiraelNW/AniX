package com.miraelDev.vauma.presentation.animeInfoDetailAndPlay

import com.google.common.collect.ImmutableList
import com.miraelDev.vauma.data.remote.FailureCauses
import com.miraelDev.vauma.domain.models.anime.AnimeDetailInfo

sealed class AnimeDetailScreenState {

    object Loading : AnimeDetailScreenState()

    object Initial : AnimeDetailScreenState()

    data class SearchFailure(val failure: FailureCauses) : AnimeDetailScreenState()

    data class SearchResult(val result: ImmutableList<AnimeDetailInfo>) : AnimeDetailScreenState()

}
