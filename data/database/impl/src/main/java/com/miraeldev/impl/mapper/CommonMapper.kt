package com.miraeldev.impl.mapper

import com.miraeldev.anime.VideoInfo
import com.miraeldev.extensions.toBoolean
import com.miraeldev.extensions.toLong
import com.miraeldev.impl.models.animeDataModels.GenreDataModel
import com.miraeldev.impl.models.animeDataModels.VideoInfoDataModel
import com.miraeldev.impl.models.animeDataModels.toGenre
import com.miraeldev.impl.models.user.ImageDbModel
import com.miraeldev.impl.models.user.toDbModel
import com.miraeldev.impl.models.user.toModel
import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.anime.Genre
import com.miraeldev.models.dto.GenreDto
import com.miraeldev.models.dto.ImageModelDto
import com.miraeldev.models.dto.VideoDto
import kotlinx.collections.immutable.toImmutableList
import tables.FavouriteAnimeInfoDbModel

internal fun GenreDto.toGenre(): Genre {
    return Genre(
        nameRu = this.nameRu,
        nameEn = this.nameEn
    )
}

internal fun Genre.toGenreDataModel(): GenreDataModel {
    return GenreDataModel(
        nameRu = this.nameRu,
        nameEn = this.nameEn
    )
}

internal fun GenreDto.toGenreDataModel(): GenreDataModel {
    return GenreDataModel(
        nameRu = this.nameRu,
        nameEn = this.nameEn
    )
}

internal fun VideoDto.toDataModel(): VideoInfoDataModel {
    return VideoInfoDataModel(
        id = id,
        videoName = videoName,
        videoImage = videoImage,
        videoUrl480 = videoUrl480,
        videoUrl720 = videoUrl720,
        videoUrl1080 = videoUrl1080
    )
}

internal fun VideoInfo.toDataModel(): VideoInfoDataModel {
    return VideoInfoDataModel(
        id = id,
        videoName = videoName,
        videoImage = videoImage,
        videoUrl480 = videoUrl480,
        videoUrl720 = videoUrl720,
        videoUrl1080 = videoUrl1080
    )
}

internal fun VideoInfoDataModel.toDomainModel(): VideoInfo {
    return VideoInfo(
        id = id,
        videoName = videoName,
        videoImage = videoImage,
        videoUrl480 = videoUrl480,
        videoUrl720 = videoUrl720,
        videoUrl1080 = videoUrl1080
    )
}

internal fun ImageModelDto.toDbModel(): ImageDbModel {
    return ImageDbModel(
        original = this.original,
        preview = this.preview
    )
}

internal fun AnimeInfo.toFavouriteAnimeDbModel(): FavouriteAnimeInfoDbModel {
    return FavouriteAnimeInfoDbModel(
        id = this.id.toLong(),
        nameEn = this.nameEn,
        nameRu = this.nameRu,
        image = this.image.toDbModel(),
        kind = this.kind,
        score = this.score.toDouble(),
        status = this.status,
        rating = this.rating,
        releasedOn = this.releasedOn,
        episodes = this.episodes.toLong(),
        duration = this.duration.toLong(),
        description = this.description,
        videoUrls = this.videoUrls.toDataModel(),
        genres = this.genres.map { it.toDbModel() },
        isFavourite = this.isFavourite.toLong(),
        page = 0
    )
}

fun FavouriteAnimeInfoDbModel.toAnimeInfo(): AnimeInfo {
    return AnimeInfo(
        id = this.id.toInt(),
        nameRu = this.nameRu,
        nameEn = this.nameEn,
        description = this.description,
        rating = this.rating,
        score = this.score.toFloat(),
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenre() }.toImmutableList(),
        episodes = this.episodes.toInt(),
        duration = this.duration.toInt(),
        image = this.image.toModel(),
        isFavourite = this.isFavourite.toBoolean()
    )
}