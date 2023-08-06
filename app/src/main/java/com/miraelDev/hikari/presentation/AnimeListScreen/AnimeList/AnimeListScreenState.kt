package com.miraelDev.hikari.presentation.AnimeListScreen.AnimeList

import com.miraelDev.hikari.domain.models.AnimeInfo

sealed class AnimeListScreenState {

    object Loading : AnimeListScreenState()

    data class AnimeList(val animes: List<AnimeInfo>) : AnimeListScreenState()

}
