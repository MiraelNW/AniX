package com.miraeldev.data.local

import androidx.room.TypeConverter
import com.miraeldev.anime.Genre
import com.miraeldev.anime.VideoInfo
import com.miraeldev.domain.models.animeDataModels.GenreDataModel
import com.miraeldev.domain.models.animeDataModels.VideoInfoDataModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
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