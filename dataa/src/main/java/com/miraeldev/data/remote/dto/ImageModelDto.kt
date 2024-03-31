package com.miraeldev.data.remote.dto

import com.miraeldev.anime.ImageModel
import com.miraeldev.local.models.user.ImageDbModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageModelDto(
    @SerialName("preview") val preview: String,
    @SerialName("original") val original: String
)

fun ImageModelDto.toModel(): ImageModel {
    return ImageModel(
        preview = this.preview,
        original = this.original,
    )
}

fun ImageModelDto.toDbModel() : ImageDbModel {
    return ImageDbModel(
        preview = this.preview,
        original = this.original,
    )
}
