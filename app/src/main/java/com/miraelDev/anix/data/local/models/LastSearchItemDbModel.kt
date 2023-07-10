package com.miraelDev.anix.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LastSearchAnimeTable")
data class LastSearchItemDbModel(
    @PrimaryKey
    val id: Int,
    val nameEn: String = "Kimetsu No Yaiba",
    val nameRu: String = "Истерибитель демонов",
    val image: String = "https://shikimori.me/system/animes/original/9999.jpg",
)