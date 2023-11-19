package com.miraeldev.data.remote.dto

import com.miraeldev.anime.ImageModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageModelDto(
    @SerialName("preview") val preview: String,
    @SerialName("original") val original: String
)

fun ImageModelDto.toModel(token: String): ImageModel {
    return ImageModel(
        preview = this.preview,
        original = this.original,
        token = "Bearer $token"
    )
}
