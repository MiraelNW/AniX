package com.miraelDev.vauma.data.local.models.favourite

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.common.collect.ImmutableList
import com.miraelDev.vauma.domain.models.anime.AnimeInfo
import com.miraelDev.vauma.domain.models.anime.Genre
import com.miraelDev.vauma.domain.models.anime.VideoInfo
import kotlinx.serialization.Serializable


@Entity(tableName = "favList")
@Serializable
data class AnimeInfoDbModel(

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
    var page:Int
)

fun AnimeInfoDbModel.toAnimeInfo(): AnimeInfo {
    return  AnimeInfo(
        id = this.id,
        nameRu = this.nameRu,
        nameEn = this.nameEn,
        description = this.description,
        rating = this.rating,
        score = this.score,
        airedOn = this.airedOn,
        status = this.status,
        kind = this.kind,
        genres = ImmutableList.copyOf(this.genres),
        episodes = this.episodes,
        duration = this.duration,
        image = this.image
    )
}

