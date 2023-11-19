package com.miraeldev.data.local.models

import com.miraeldev.anime.ImageModel
import com.miraeldev.data.remote.dto.ImageModelDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDbModel(
    val preview: String,
    val original: String
)

fun ImageDbModel.toModel(): ImageModel {
    return ImageModel(
        preview = this.preview,
        original = this.original
    )
}

fun ImageModelDto.toDbModel(): ImageDbModel {
    return ImageDbModel(
        preview = this.preview,
        original = this.original
    )
}
