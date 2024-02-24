package com.miraeldev.data.mapper

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.anime.ImageModel
import com.miraeldev.anime.VideoInfo
import com.miraeldev.data.local.models.favourite.AnimeInfoDbModel
import com.miraeldev.data.local.models.user.ImageDbModel
import com.miraeldev.models.anime.Genre
import com.miraeldev.models.models.animeDataModels.GenreDataModel
import com.miraeldev.models.models.animeDataModels.VideoInfoDataModel
import me.tatarka.inject.annotations.Inject

@Inject
internal class AnimeModelsMapper{


    fun mapAnimeInfoToAnimeDbModel(animeInfo: AnimeInfo): AnimeInfoDbModel {
        return AnimeInfoDbModel(
            id = animeInfo.id,
            nameEn = animeInfo.nameEn,
            nameRu = animeInfo.nameRu,
            image = mapImageModelToDataModel(animeInfo.image),
            kind = animeInfo.kind,
            score = animeInfo.score,
            status = animeInfo.status,
            rating = animeInfo.rating,
            releasedOn = animeInfo.releasedOn,
            episodes = animeInfo.episodes,
            duration = animeInfo.duration,
            description = animeInfo.description,
            videoUrls = mapVideoInfoToDataModel(animeInfo.videoUrls),
            genres = animeInfo.genres.map { mapGenreToDataModel(it) },
            isFavourite = animeInfo.isFavourite,
            page = 0
        )
    }

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