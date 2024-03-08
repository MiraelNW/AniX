package com.miraeldev.local

import androidx.room.TypeConverter
import com.miraeldev.anime.ImageModel
import com.miraeldev.anime.VideoInfo
import com.miraeldev.local.animeDataModels.GenreDataModel
import com.miraeldev.local.models.user.ImageDbModel
import com.miraeldev.local.models.user.LastWatchedAnimeDbModel
import com.miraeldev.models.anime.Genre
import com.miraeldev.models.models.animeDataModels.VideoInfoDataModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class Converters {

    @TypeConverter
    fun videoInfoToString(videoInfo: VideoInfoDataModel): String {
        return Json.encodeToString(videoInfo)
    }

    @TypeConverter
    fun stringToVideoInfoDataModel(value: String): VideoInfoDataModel {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun lastWatchedAnimeToString(lastWatchedAnimeDbModel: LastWatchedAnimeDbModel?): String {
        return Json.encodeToString(lastWatchedAnimeDbModel)
    }

    @TypeConverter
    fun stringToLastWatchedAnime(value: String): LastWatchedAnimeDbModel? {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun imageDbModelToString(imageModel: ImageDbModel): String {
        return Json.encodeToString(imageModel)
    }

    @TypeConverter
    fun stringToImageDbModel(value: String): ImageDbModel {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun stringToImageModel(value: String): ImageModel {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun stringToVideoInfo(value: String): VideoInfo {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun genreDataModelToString(genre: List<GenreDataModel>): String {
        return Json.encodeToString(genre)
    }

    @TypeConverter
    fun stringToGenreDataModel(value: String): ImmutableList<GenreDataModel> {
        val list = Json.decodeFromString<List<GenreDataModel>>(value)
        return list.toImmutableList()
    }

    @TypeConverter
    fun stringToGenre(value: String): ImmutableList<Genre> {
        val list = Json.decodeFromString<List<Genre>>(value)
        return list.toImmutableList()
    }
}