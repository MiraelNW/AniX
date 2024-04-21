package com.miraeldev.impl.remote.dto

import com.miraeldev.anime.ImageModel
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
