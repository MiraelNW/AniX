package com.miraelDev.vauma.data.remote.dto

import com.miraelDev.vauma.domain.models.anime.Similar
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimilarDto(
    @SerialName("id")
    val id: Int,
    @SerialName("kind")
    val kind: String,
    @SerialName("name")
    val nameEn: String,
    @SerialName("image")
    val image: String,
    @SerialName("score")
    val score: Float,
    @SerialName("russian")
    val nameRu: String,
)

fun SimilarDto.toSimilar(): Similar {
    return Similar(
        id = this.id,
        kind = this.kind,
        nameEn = this.nameEn,
        nameRu = this.nameRu,
        image = this.image,
        score = this.score
    )
}
