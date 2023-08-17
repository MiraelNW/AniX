package com.miraelDev.hikari.data.local.models

import com.google.common.collect.ImmutableList
import com.miraelDev.hikari.domain.models.VideoInfo


data class AnimeInfoDbModel(

        val id: Int,
        val nameEn: String = "Kimetsu No Yaiba",
        val nameRu: String = "Истерибитель демонов",
        val image: String = "https://shikimori.me/system/animes/original/9999.jpg",
        val kind: String = "tv",
        val score: Float = 8.51f,
        val status: String = "released",
        val rating: String = "r_plus",
        val episodes: Int = 26,
        val duration: Int = 23,
        val description: String = "Действие происходит в эпоху Тайсё. Ещё с древних времён ходят слухи о том, что в лесу обитают человекоподобные демоны, которые питаются людьми и ходят по ночам, ища новую жертву. Но... это же просто легенда, ведь так?..\n" +
                "Тандзиро Камадо — старший сын в семье, потерявший своего отца и взявший на себя заботу о своих родных. Однажды он уходит в соседний город, чтобы продать древесный уголь. Вернувшись утром, парень обнаруживает перед собой страшную картину: вся родня была зверски убита, а единственной выжившей является Нэдзуко — обращённая в демона, но пока не потерявшая всю человечность младшая сестра.\n" +
                "С этого момента для Тандзиро и Нэдзуко начинается долгое и опасное путешествие, в котором мальчик намерен разыскать убийцу и узнать способ исцеления для своей сестры. Но в состоянии ли дети преодолеть все трудности и вернуться домой?",
        val videoUrls: VideoInfo = VideoInfo(),
        val genres: List<String> = listOf(
                "Comedy",
                "Fantasy",
                "Triller",
                "Comedy",
                "Fantasy",
                "Triller"
        )
)
