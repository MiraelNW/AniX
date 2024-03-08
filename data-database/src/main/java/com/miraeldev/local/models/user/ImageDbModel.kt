package com.miraeldev.local.models.user

import com.miraeldev.anime.ImageModel
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

fun ImageModel.toDbModel(): ImageDbModel {
    return ImageDbModel(
        preview = this.preview,
        original = this.original
    )
}
