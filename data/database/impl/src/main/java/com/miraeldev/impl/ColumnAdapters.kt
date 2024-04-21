package com.miraeldev.impl

import app.cash.sqldelight.ColumnAdapter
import com.miraeldev.impl.models.animeDataModels.GenreDataModel
import com.miraeldev.impl.models.animeDataModels.VideoInfoDataModel
import com.miraeldev.impl.models.user.ImageDbModel
import com.miraeldev.impl.models.user.LastWatchedAnimeDbModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val imageAdapter = object : ColumnAdapter<ImageDbModel, String> {
    override fun encode(value: ImageDbModel): String {
        return Json.encodeToString(value)
    }

    override fun decode(databaseValue: String): ImageDbModel {
        return Json.decodeFromString<ImageDbModel>(databaseValue)
    }
}

val videoUrlsAdapter = object : ColumnAdapter<VideoInfoDataModel, String> {
    override fun encode(value: VideoInfoDataModel): String {
        return Json.encodeToString(value)
    }

    override fun decode(databaseValue: String): VideoInfoDataModel {
        return Json.decodeFromString<VideoInfoDataModel>(databaseValue)
    }
}

val genresAdapter = object : ColumnAdapter<List<GenreDataModel>, String> {
    override fun encode(value: List<GenreDataModel>): String {
        return Json.encodeToString(value)
    }

    override fun decode(databaseValue: String): List<GenreDataModel> {
        return Json.decodeFromString<List<GenreDataModel>>(databaseValue)
    }
}

val lastWatchedAnimeAdapter = object : ColumnAdapter<LastWatchedAnimeDbModel, String> {
    override fun encode(value: LastWatchedAnimeDbModel): String {
        return Json.encodeToString(value)
    }

    override fun decode(databaseValue: String): LastWatchedAnimeDbModel {
        return Json.decodeFromString<LastWatchedAnimeDbModel>(databaseValue)
    }
}