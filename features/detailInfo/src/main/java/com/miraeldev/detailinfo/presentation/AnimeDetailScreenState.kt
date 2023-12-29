package com.miraeldev.detailinfo.presentation

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.result.FailureCauses
import kotlinx.collections.immutable.ImmutableList

sealed class AnimeDetailScreenState {

    data object Loading : AnimeDetailScreenState()

    data object Initial : AnimeDetailScreenState()

    data class SearchFailure(val failure: FailureCauses) : AnimeDetailScreenState()

    data class SearchResult(val result: ImmutableList<AnimeDetailInfo>) : AnimeDetailScreenState()

}
