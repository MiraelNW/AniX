package com.miraeldev.impl.mapper

import com.miraeldev.extensions.toBoolean
import com.miraeldev.extensions.toLong
import com.miraeldev.impl.models.animeDataModels.VideoInfoDataModel
import com.miraeldev.impl.models.animeDataModels.toGenre
import com.miraeldev.impl.models.user.toDbModel
import com.miraeldev.impl.models.user.toModel
import com.miraeldev.models.paging.LastDbNode
import com.miraeldev.models.paging.PagingAnimeInfo
import kotlinx.collections.immutable.toPersistentList
import tables.filmcategory.FilmCategoryPagingInfoDbModel
import tables.initialsearch.InitialSearchPagingInfoDbModel
import tables.namecategory.NameCategoryPagingInfoDbModel
import tables.newcategory.NewCategoryPagingInfoDbModel
import tables.popularcategory.PopularCategoryPagingInfoDbModel

internal fun PagingAnimeInfo.mapToInitialSearchDbModel(): InitialSearchPagingInfoDbModel {
    return InitialSearchPagingInfoDbModel(
        id = this.id.toLong(),
        nameRu = this.nameRu,
        nameEn = this.nameEn,
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
        videoUrls = this.videos.toDataModel(),
        isFavourite = this.isFavourite.toLong(),
        page = 0,
        isLast = 0,
        createAt = 0
    )
}

internal fun PagingAnimeInfo.mapToNewCategoryDbModel(): NewCategoryPagingInfoDbModel {
    return NewCategoryPagingInfoDbModel(
        id = this.id.toLong(),
        nameRu = this.nameRu,
        nameEn = this.nameEn,
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
        videoUrls = this.videos.toDataModel() ?: VideoInfoDataModel(),
        isFavourite = this.isFavourite.toLong(),
        page = 0,
        isLast = 0,
        createAt = 0
    )
}

internal fun PagingAnimeInfo.mapToPopularCategoryDbModel(): PopularCategoryPagingInfoDbModel {
    return PopularCategoryPagingInfoDbModel(
        id = this.id.toLong(),
        nameRu = this.nameRu,
        nameEn = this.nameEn,
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
        videoUrls = this.videos.toDataModel() ?: VideoInfoDataModel(),
        isFavourite = this.isFavourite.toLong(),
        page = 0,
        isLast = 0,
        createAt = 0
    )
}

internal fun PagingAnimeInfo.mapToNameCategoryDbModel(): NameCategoryPagingInfoDbModel {
    return NameCategoryPagingInfoDbModel(
        id = this.id.toLong(),
        nameRu = this.nameRu,
        nameEn = this.nameEn,
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
        videoUrls = this.videos.toDataModel() ?: VideoInfoDataModel(),
        isFavourite = this.isFavourite.toLong(),
        page = 0,
        isLast = 0,
        createAt = 0
    )
}

internal fun PagingAnimeInfo.mapToFilmCategoryDbModel(): FilmCategoryPagingInfoDbModel {
    return FilmCategoryPagingInfoDbModel(
        id = this.id.toLong(),
        nameRu = this.nameRu,
        nameEn = this.nameEn,
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
        videoUrls = this.videos.toDataModel() ?: VideoInfoDataModel(),
        isFavourite = this.isFavourite.toLong(),
        page = 0,
        isLast = 0,
        createAt = 0
    )
}

internal fun NewCategoryPagingInfoDbModel.toPagingAnimeInfo(): PagingAnimeInfo {
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

internal fun PopularCategoryPagingInfoDbModel.toPagingAnimeInfo(): PagingAnimeInfo {
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

internal fun NameCategoryPagingInfoDbModel.toPagingAnimeInfo(): PagingAnimeInfo {
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

internal fun FilmCategoryPagingInfoDbModel.toPagingAnimeInfo(): PagingAnimeInfo {
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

internal fun NewCategoryPagingInfoDbModel?.toLastDbNode(): LastDbNode {
    return LastDbNode(
        isLast = this?.isLast?.toBoolean() ?: true,
        page = this?.page ?: 0L,
        createAt = this?.createAt ?: 0
    )
}

internal fun PopularCategoryPagingInfoDbModel?.toLastDbNode(): LastDbNode {
    return LastDbNode(
        isLast = this?.isLast?.toBoolean() ?: true,
        page = this?.page ?: 0L,
        createAt = this?.createAt ?: 0
    )
}

internal fun NameCategoryPagingInfoDbModel?.toLastDbNode(): LastDbNode {
    return LastDbNode(
        isLast = this?.isLast?.toBoolean() ?: true,
        page = this?.page ?: 0L,
        createAt = this?.createAt ?: 0
    )
}

internal fun FilmCategoryPagingInfoDbModel?.toLastDbNode(): LastDbNode {
    return LastDbNode(
        isLast = this?.isLast?.toBoolean() ?: true,
        page = this?.page ?: 0L,
        createAt = this?.createAt ?: 0
    )
}

internal fun InitialSearchPagingInfoDbModel?.toLastDbNode(): LastDbNode {
    return LastDbNode(
        isLast = this?.isLast?.toBoolean() ?: true,
        page = this?.page ?: 0L,
        createAt = this?.createAt ?: 0
    )
}