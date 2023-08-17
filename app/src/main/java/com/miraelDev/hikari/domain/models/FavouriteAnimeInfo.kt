package com.miraelDev.hikari.domain.models

import androidx.compose.runtime.Stable
import com.google.common.collect.ImmutableList
import okhttp3.internal.immutableListOf

@Stable
data class FavouriteAnimeInfo(
    val id: Int,
    val nameRu: String = "Истерибитель демонов",
    val image: String = "https://shikimori.me/system/animes/original/9999.jpg",
)
