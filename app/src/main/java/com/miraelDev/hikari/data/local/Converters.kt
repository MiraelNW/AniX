package com.miraelDev.hikari.data.local

import androidx.room.TypeConverter
import com.google.common.collect.ImmutableList
import com.miraelDev.hikari.domain.models.VideoInfo
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
class Converters {

    @TypeConverter
    fun stringToListOfStrings(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun listOfStringsToString(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun videoInfoToString(videoInfo: VideoInfo): String {
        return Json.encodeToString(videoInfo)
    }

    @TypeConverter
    fun stringToVideoInfo(value: String): VideoInfo {
        return Json.decodeFromString(value)
    }
}