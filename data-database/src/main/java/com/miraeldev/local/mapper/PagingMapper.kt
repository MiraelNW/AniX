package com.miraeldev.local.mapper

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.extensions.toBoolean
import com.miraeldev.extensions.toLong
import com.miraeldev.local.animeDataModels.toGenre
import com.miraeldev.local.models.filmCategory.PagingFilmCategoryAnimeInfoDbModel
import com.miraeldev.local.models.nameCategory.PagingNameCategoryAnimeInfoDbModel
import com.miraeldev.local.models.newCategory.PagingNewCategoryAnimeInfoDbModel
import com.miraeldev.local.models.popularCategory.PagingPopularCategoryAnimeInfoDbModel
import com.miraeldev.local.models.user.toModel
import com.miraeldev.models.dto.AnimeInfoDto
import com.miraeldev.models.models.animeDataModels.VideoInfoDataModel
import com.miraeldev.models.paging.LastDbNode
import com.miraeldev.models.paging.PagingAnimeInfo
import kotlinx.collections.immutable.toPersistentList
import tables.initialsearch.InitialSearchPagingInfoDbModel

internal fun AnimeInfoDto.mapToPagingNewCategoryModel(): PagingNewCategoryAnimeInfoDbModel {
    return PagingNewCategoryAnimeInfoDbModel(
        id = this.id,
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score,
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenreDataModel() },
        episodes = this.episodes,
        image = this.image.toDbModel(),
        duration = this.duration,
        isFavourite = this.isFavourite,
        page = 0
    )
}

internal fun AnimeInfoDto.mapToPagingPopularCategoryModel(): PagingPopularCategoryAnimeInfoDbModel {
    return PagingPopularCategoryAnimeInfoDbModel(
        id = this.id,
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score,
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenreDataModel() },
        episodes = this.episodes,
        image = this.image.toDbModel(),
        duration = this.duration,
        isFavourite = this.isFavourite,
        page = 0
    )
}

internal fun AnimeInfoDto.mapToPagingFilmCategoryModel(): PagingFilmCategoryAnimeInfoDbModel {
    return PagingFilmCategoryAnimeInfoDbModel(
        id = this.id,
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score,
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenreDataModel() },
        episodes = this.episodes,
        image = this.image.toDbModel(),
        duration = this.duration,
        isFavourite = this.isFavourite,
        page = 0
    )
}

internal fun AnimeInfoDto.mapToPagingNameCategoryModel(): PagingNameCategoryAnimeInfoDbModel {
    return PagingNameCategoryAnimeInfoDbModel(
        id = this.id,
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score,
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenreDataModel() },
        episodes = this.episodes,
        image = this.image.toDbModel(),
        duration = this.duration,
        isFavourite = this.isFavourite,
        page = 0
    )
}

internal fun AnimeInfoDto.mapToInitialSearchModel(): InitialSearchPagingInfoDbModel {
    return InitialSearchPagingInfoDbModel(
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
        page = 0,
        isLast = 0,
        createAt = 0
    )
}

internal fun InitialSearchPagingInfoDbModel.toPagingAnimeInfo(): PagingAnimeInfo {
    return PagingAnimeInfo(
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
        videos = this.videoUrls.toDomainModel(),
        isFavourite = this.isFavourite.toBoolean(),
        page = this.page,
        isLast = this.isLast.toBoolean(),
        createAt = this.createAt
    )
}

internal fun InitialSearchPagingInfoDbModel?.toLastDbNode(): LastDbNode {
    return LastDbNode(
        isLast = this?.isLast?.toBoolean() ?: true,
        page = this?.page ?: 0L,
        createAt = this?.createAt ?: 0
    )
}