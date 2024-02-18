package com.miraeldev.favourites.presentation.favouriteComponent

import com.miraeldev.anime.AnimeInfo
import kotlinx.coroutines.flow.StateFlow

interface FavouriteComponent {

    val model: StateFlow<FavouriteStore.State>

    fun onAnimeItemClick(id: Int)
    fun navigateToSearchScreen(search: String)
    fun updateSearchTextState(search: String)
    fun selectAnimeItem(animeInfo: AnimeInfo)
    fun searchAnimeItemInDatabase(name: String)
    fun searchAnimeByName(name: String)
    fun loadAnimeList()

}