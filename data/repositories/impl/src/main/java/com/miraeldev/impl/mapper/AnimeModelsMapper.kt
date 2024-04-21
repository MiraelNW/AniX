package com.miraeldev.impl.mapper

import com.miraeldev.anime.ImageModel
import com.miraeldev.anime.VideoInfo
import com.miraeldev.local.animeDataModels.GenreDataModel
import com.miraeldev.local.models.user.ImageDbModel
import com.miraeldev.models.anime.Genre
import com.miraeldev.models.models.animeDataModels.VideoInfoDataModel
import me.tatarka.inject.annotations.Inject

@Inject
class AnimeModelsMapper{


    private fun mapVideoInfoToDataModel(videoInfo: VideoInfo): VideoInfoDataModel {
        return VideoInfoDataModel(
            id = videoInfo.id,
            videoImage = videoInfo.videoImage,
            videoUrl480 = videoInfo.videoUrl480,
            videoUrl720 = videoInfo.videoUrl720,
            videoUrl1080 = videoInfo.videoUrl1080,
            videoName = videoInfo.videoName,
        )
    }


    private fun mapGenreToDataModel(genre: Genre): GenreDataModel {
        return GenreDataModel(
            nameRu = genre.nameRu,
            nameEn = genre.nameEn,
        )
    }

    private fun mapImageModelToDataModel(image: ImageModel): ImageDbModel {
        return ImageDbModel(
            original = image.original,
            preview = image.preview,
        )
    }
}