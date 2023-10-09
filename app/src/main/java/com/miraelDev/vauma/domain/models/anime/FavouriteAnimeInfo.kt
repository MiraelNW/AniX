package com.miraelDev.vauma.domain.models.anime

import androidx.compose.runtime.Stable

@Stable
data class FavouriteAnimeInfo(
    val id: Int,
    val nameRu: String = "Истерибитель демонов",
    val image: String = "https://shikimori.me/system/animes/original/9999.jpg",
)
