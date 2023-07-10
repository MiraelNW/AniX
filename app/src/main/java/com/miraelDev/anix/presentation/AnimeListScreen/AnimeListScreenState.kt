package com.miraelDev.anix.presentation.AnimeListScreen

import com.miraelDev.anix.domain.models.AnimeInfo

sealed class AnimeListScreenState {

    object Loading : AnimeListScreenState()

    object Initial : AnimeListScreenState()

    data class AnimeList(val animes: List<AnimeInfo>) : AnimeListScreenState()

}
