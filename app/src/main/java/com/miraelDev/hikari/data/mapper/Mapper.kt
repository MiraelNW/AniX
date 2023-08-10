package com.miraelDev.hikari.data.mapper

import com.miraelDev.hikari.data.remote.dto.AnimeInfoDto
import com.miraelDev.hikari.domain.models.AnimeInfo
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapAnimeListDtoToListAnimeInfo(animes: List<AnimeInfoDto>): List<AnimeInfo> {
        return animes.map {
            AnimeInfo(
                    id = it.id,
                    nameEn = it.name,
                    nameRu = it.russianName,
                    kind = it.kind,
                    description = it.description,
                    episodes = it.episodes,
                    image = it.image,
                    score = it.score,
                    status = it.status,
                    rating = it.rating,
            )
        }
    }


}