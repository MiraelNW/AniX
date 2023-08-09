package com.miraelDev.hikari.data.local.models



//@Entity(tableName = "LastSearchAnimeTable")
data class LastSearchItemDbModel(
//    @PrimaryKey
    val id: Int,
    val nameEn: String = "Kimetsu No Yaiba",
    val nameRu: String = "Истерибитель демонов",
    val image: String = "https://shikimori.me/system/animes/original/9999.jpg",
)