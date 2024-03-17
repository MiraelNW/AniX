package com.miraeldev.local.mapper

import com.miraeldev.local.models.filmCategory.PagingFilmCategoryAnimeInfoDbModel
import com.miraeldev.local.models.initialSearch.PagingInitialSearchAnimeInfoDbModel
import com.miraeldev.local.models.nameCategory.PagingNameCategoryAnimeInfoDbModel
import com.miraeldev.local.models.newCategory.PagingNewCategoryAnimeInfoDbModel
import com.miraeldev.local.models.popularCategory.PagingPopularCategoryAnimeInfoDbModel
import com.miraeldev.models.dto.AnimeInfoDto

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

internal fun AnimeInfoDto.mapToInitialSearchModel(): PagingInitialSearchAnimeInfoDbModel {
    return PagingInitialSearchAnimeInfoDbModel(
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