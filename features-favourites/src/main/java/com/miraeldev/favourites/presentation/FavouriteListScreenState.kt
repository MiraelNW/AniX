package com.miraeldev.favourites.presentation

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.result.FailureCauses
import kotlinx.collections.immutable.ImmutableList

sealed class FavouriteListScreenState {

    data object Loading : FavouriteListScreenState()

    data object Initial : FavouriteListScreenState()

    data class Failure(val failure: FailureCauses) : FavouriteListScreenState()

    data class Result(val result: ImmutableList<AnimeInfo>) : FavouriteListScreenState()

}