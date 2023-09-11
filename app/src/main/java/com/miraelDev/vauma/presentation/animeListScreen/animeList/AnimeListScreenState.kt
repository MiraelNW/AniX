package com.miraelDev.vauma.presentation.animeListScreen.animeList

import com.miraelDev.vauma.domain.models.AnimeInfo

sealed class AnimeListScreenState {

    object Loading : AnimeListScreenState()
    data class Failure(val failureCause : Int) : AnimeListScreenState()

    data class AnimeList(val animes: List<AnimeInfo>) : AnimeListScreenState()

}
