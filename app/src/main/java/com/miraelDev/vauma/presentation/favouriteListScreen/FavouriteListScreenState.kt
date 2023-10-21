package com.miraelDev.vauma.presentation.favouriteListScreen

import com.google.common.collect.ImmutableList
import com.miraelDev.vauma.data.remote.FailureCauses
import com.miraelDev.vauma.domain.models.anime.AnimeInfo

sealed class FavouriteListScreenState {

    object Loading : FavouriteListScreenState()

    object Initial : FavouriteListScreenState()

    data class Failure(val failure: FailureCauses) : FavouriteListScreenState()

    data class Result(val result: ImmutableList<AnimeInfo>) : FavouriteListScreenState()

}