package com.miraelDev.hikari.data.mapper

import com.google.common.collect.ImmutableList
import com.miraelDev.hikari.data.local.models.AnimeInfoDbModel
import com.miraelDev.hikari.data.remote.FailureCauses
import com.miraelDev.hikari.data.remote.dto.AnimeInfoDto
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.models.FavouriteAnimeInfo
import com.miraelDev.hikari.domain.result.Result
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

    fun mapAnimeInfoToFavouriteAnimeDbModel(animeInfo: AnimeInfo) =
            AnimeInfoDbModel(
                    id = animeInfo.id,
                    nameRu = animeInfo.nameRu,
                    nameEn = animeInfo.nameEn,
                    description = animeInfo.description,
                    rating = animeInfo.rating,
                    score = animeInfo.score,
                    status = animeInfo.status,
                    kind = animeInfo.kind,
                    genres = animeInfo.genres,
                    episodes = animeInfo.episodes,
                    duration = animeInfo.duration,
                    image = animeInfo.image,
            )

    fun mapAnimeInfoDbModelListIntoResult(list : List<AnimeInfoDbModel>):Result{
        return if (list.isEmpty()) {
            Result.Failure(failureCause = FailureCauses.NotFound)
        } else {
            Result.Success(
                    animeList = list.map { item ->
                        AnimeInfo(
                                id = item.id,
                                nameRu = item.nameRu,
                                nameEn = item.nameEn,
                                description = item.description,
                                rating = item.rating,
                                score = item.score,
                                status = item.status,
                                kind = item.kind,
                                genres = ImmutableList.copyOf(item.genres),
                                episodes = item.episodes,
                                duration = item.duration,
                                image = item.image,
                        )
                    }
            )
        }
    }
    fun mapAnimeInfoToFavouriteAnimeDbModel(animeInfo: FavouriteAnimeInfo) =
            AnimeInfoDbModel(
                    id = animeInfo.id,
                    nameRu = animeInfo.nameRu,
                    image = animeInfo.image
            )

    fun mapFavouriteAnimeDbModelToFavouriteAnimeInfo(animeInfo: AnimeInfoDbModel) =
            FavouriteAnimeInfo(
                    id = animeInfo.id,
                    nameRu = animeInfo.nameRu,
                    image = animeInfo.image
            )
}