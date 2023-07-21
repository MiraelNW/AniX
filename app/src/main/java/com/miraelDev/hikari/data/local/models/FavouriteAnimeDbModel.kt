package com.miraelDev.hikari.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavouriteAnimeTable")
data class FavouriteAnimeDbModel(
    @PrimaryKey
    val id: Int,
    val nameEn: String = "Kimetsu No Yaiba",
    val nameRu: String = "Истерибитель демонов",
    val image: String = "https://shikimori.me/system/animes/original/9999.jpg",
    val kind: String = "tv",
    val score: Float = 8.51f,
    val status: String = "released",
    val episodes: Int = 26
)
