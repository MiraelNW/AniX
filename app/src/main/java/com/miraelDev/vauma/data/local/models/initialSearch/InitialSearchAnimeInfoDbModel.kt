package com.miraelDev.vauma.data.local.models.initialSearch

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miraelDev.vauma.domain.models.Genre
import com.miraelDev.vauma.domain.models.VideoInfo
import kotlinx.serialization.Serializable


@Entity(tableName = "initial_search_anime")
@Serializable
data class InitialSearchAnimeInfoDbModel(

    @PrimaryKey val id: Int,
    val nameEn: String,
    val nameRu: String,
    val image: String,
    val kind: String,
    val score: Float,
    val status: String,
    val rating: String,
    val airedOn: String,
    val episodes: Int,
    val duration: Int,
    val description: String,
    val videoUrls: VideoInfo = VideoInfo(),
    val genres: List<Genre>,
    var page: Int,
)
