package com.miraeldev.local.mapper

import com.miraeldev.anime.VideoInfo
import com.miraeldev.local.animeDataModels.GenreDataModel
import com.miraeldev.local.models.user.ImageDbModel
import com.miraeldev.models.anime.Genre
import com.miraeldev.models.dto.GenreDto
import com.miraeldev.models.dto.ImageModelDto
import com.miraeldev.models.dto.VideoDto
import com.miraeldev.models.models.animeDataModels.VideoInfoDataModel


internal fun GenreDto.toGenre(): Genre {
    return Genre(
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