package com.miraeldev.impl.mapper

import com.miraeldev.extensions.toBoolean
import com.miraeldev.extensions.toLong
import com.miraeldev.impl.models.animeDataModels.VideoInfoDataModel
import com.miraeldev.impl.models.animeDataModels.toGenre
import com.miraeldev.impl.models.user.toModel
import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto
import kotlinx.collections.immutable.toPersistentList
import tables.filmcategory.FilmCategoryAnimeInfoDbModel
import tables.namecategory.NameCategoryAnimeInfoDbModel
import tables.newcategory.NewCategoryAnimeInfoDbModel
import tables.popularcategory.PopularCategoryAnimeInfoDbModel

internal fun FilmCategoryAnimeInfoDbModel.toAnimeInfo(): AnimeInfo {
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
        genres = this.genres.map { list -> list.toGenre() }.toPersistentList(),
        episodes = this.episodes.toInt(),
        image = this.image.toModel(),
        duration = this.duration.toInt(),
        videoUrls = this.videoUrls.toDomainModel(),
        isFavourite = this.isFavourite.toBoolean()
    )
}
internal fun NameCategoryAnimeInfoDbModel.toAnimeInfo(): AnimeInfo {
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
        genres = this.genres.map { list -> list.toGenre() }.toPersistentList(),
        episodes = this.episodes.toInt(),
        image = this.image.toModel(),
        duration = this.duration.toInt(),
        videoUrls = this.videoUrls.toDomainModel(),
        isFavourite = this.isFavourite.toBoolean()
    )
}
internal fun NewCategoryAnimeInfoDbModel.toAnimeInfo(): AnimeInfo {
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
        genres = this.genres.map { list -> list.toGenre() }.toPersistentList(),
        episodes = this.episodes.toInt(),
        image = this.image.toModel(),
        duration = this.duration.toInt(),
        videoUrls = this.videoUrls.toDomainModel(),
        isFavourite = this.isFavourite.toBoolean()
    )
}
internal fun PopularCategoryAnimeInfoDbModel.toAnimeInfo(): AnimeInfo {
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
        genres = this.genres.map { list -> list.toGenre() }.toPersistentList(),
        episodes = this.episodes.toInt(),
        image = this.image.toModel(),
        duration = this.duration.toInt(),
        videoUrls = this.videoUrls.toDomainModel(),
        isFavourite = this.isFavourite.toBoolean()
    )
}

internal fun AnimeInfoDto.mapToNewCategoryModel(): NewCategoryAnimeInfoDbModel {
    return NewCategoryAnimeInfoDbModel(
        id = this.id.toLong(),
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score.toDouble(),
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenreDataModel() },
        episodes = this.episodes.toLong(),
        image = this.image.toDbModel(),
        duration = this.duration.toLong(),
        videoUrls = this.videos.firstOrNull()?.toDataModel() ?: VideoInfoDataModel(),
        isFavourite = this.isFavourite.toLong(),
        createTime = System.currentTimeMillis()
    )
}

internal fun AnimeInfoDto.mapToPopularCategoryModel(): PopularCategoryAnimeInfoDbModel {
    return PopularCategoryAnimeInfoDbModel(
        id = this.id.toLong(),
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score.toDouble(),
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenreDataModel() },
        episodes = this.episodes.toLong(),
        image = this.image.toDbModel(),
        duration = this.duration.toLong(),
        videoUrls = this.videos.firstOrNull()?.toDataModel() ?: VideoInfoDataModel(),
        isFavourite = this.isFavourite.toLong()
    )
}

internal fun AnimeInfoDto.mapToFilmCategoryModel(): FilmCategoryAnimeInfoDbModel {
    return FilmCategoryAnimeInfoDbModel(
        id = this.id.toLong(),
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score.toDouble(),
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenreDataModel() },
        episodes = this.episodes.toLong(),
        image = this.image.toDbModel(),
        duration = this.duration.toLong(),
        videoUrls = this.videos.firstOrNull()?.toDataModel() ?: VideoInfoDataModel(),
        isFavourite = this.isFavourite.toLong()
    )
}

internal fun AnimeInfoDto.mapToNameCategoryModel(): NameCategoryAnimeInfoDbModel {
    return NameCategoryAnimeInfoDbModel(
        id = this.id.toLong(),
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score.toDouble(),
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenreDataModel() },
        episodes = this.episodes.toLong(),
        image = this.image.toDbModel(),
        duration = this.duration.toLong(),
        videoUrls = this.videos.firstOrNull()?.toDataModel() ?: VideoInfoDataModel(),
        isFavourite = this.isFavourite.toLong()
    )
}