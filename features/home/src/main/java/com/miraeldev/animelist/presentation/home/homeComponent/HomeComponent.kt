package com.miraeldev.animelist.presentation.home.homeComponent

import com.miraeldev.models.anime.LastWatchedAnime
import com.miraeldev.models.user.User
import kotlinx.coroutines.flow.StateFlow

interface HomeComponent {

    val model: StateFlow<HomeStore.State>

    fun loadAnimeVideo(anime: LastWatchedAnime)
    fun addAnimeToList(user: User, isSelected: Boolean, animeItem: LastWatchedAnime)
    fun onAnimeItemClick(id: Int)
    fun onSeeAllClick(id: Int)
    fun onPlayClick(id: Int)
}