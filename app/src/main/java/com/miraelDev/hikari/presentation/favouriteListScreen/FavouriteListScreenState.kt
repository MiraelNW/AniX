package com.miraelDev.hikari.presentation.favouriteListScreen

import com.miraelDev.hikari.data.remote.FailureCauses
import com.miraelDev.hikari.domain.models.AnimeInfo

sealed class FavouriteListScreenState {

    object Loading : FavouriteListScreenState()

    object Initial : FavouriteListScreenState()

    data class Failure(val failure: FailureCauses) : FavouriteListScreenState()

    data class Result(val result: List<AnimeInfo>) : FavouriteListScreenState()

}