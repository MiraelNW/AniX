package com.miraeldev.data.remote.dto

import com.miraeldev.models.anime.Similar
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
    val image: ImageModelDto,
    @SerialName("score")
    val score: Float,
    @SerialName("russian")
    val nameRu: String,
)

internal fun SimilarDto.toVideoInfo(): Similar {
    return Similar(
        id = this.id,
        kind = this.kind,
        nameEn = this.nameEn,
        nameRu = this.nameRu,
        image = this.image.toModel(),
        score = this.score
    )
}
