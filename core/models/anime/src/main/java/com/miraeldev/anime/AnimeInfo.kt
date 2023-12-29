package com.miraeldev.anime

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class AnimeInfo(
    val id: Int,
    val nameEn: String = "Kimetsu No Yaiba",
    val nameRu: String = "Истерибитель демонов",
    val image: ImageModel = ImageModel(
        "http://10.0.2.2:8080/api/v1/anime/images/original/1",
        "http://10.0.2.2:8080/api/v1/anime/images/original/1"
    ),
    val kind: String = "tv",
    val score: Float = 8.51f,
    val status: String = "released",
    val rating: String = "r_plus",
    val releasedOn: String = "2022",
    val episodes: Int = 26,
    val duration: Int = 23,
    val description: String = "Действие происходит в эпоху Тайсё. Ещё с древних времён ходят слухи о том, что в лесу обитают человекоподобные демоны, которые питаются людьми и ходят по ночам, ища новую жертву. Но... это же просто легенда, ведь так?..\n" +
            "Тандзиро Камадо — старший сын в семье, потерявший своего отца и взявший на себя заботу о своих родных. Однажды он уходит в соседний город, чтобы продать древесный уголь. Вернувшись утром, парень обнаруживает перед собой страшную картину: вся родня была зверски убита, а единственной выжившей является Нэдзуко — обращённая в демона, но пока не потерявшая всю человечность младшая сестра.\n" +
            "С этого момента для Тандзиро и Нэдзуко начинается долгое и опасное путешествие, в котором мальчик намерен разыскать убийцу и узнать способ исцеления для своей сестры. Но в состоянии ли дети преодолеть все трудности и вернуться домой?",
    val videoUrls: VideoInfo = VideoInfo(),
    val genres: ImmutableList<Genre> = persistentListOf(
        Genre("en", "ru"),
        Genre("en2", "ru"),
        Genre("en3", "ru"),
        Genre("en4", "ru"),
        Genre("en5", "ru"),
        Genre("en6", "ru"),
    ),
    val isFavourite: Boolean
)

fun AnimeInfo.toLastWatched():LastWatchedAnime{
    return LastWatchedAnime(
        id= this.id,
        imageUrl = this.image.original,
        nameRu = this.nameRu,
        nameEn = this.nameEn,
        genres = this.genres,
        isFavourite = this.isFavourite,
        videoUrl = this.videoUrls.playerUrl[0],
        episodeNumber = 0
    )
}
