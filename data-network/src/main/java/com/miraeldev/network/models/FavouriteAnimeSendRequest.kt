package com.miraeldev.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FavouriteAnimeSendRequest(
    @SerialName("anime_id") val animeId: Long,
    @SerialName("user_id") val userId: Long,
    @SerialName("is_favourite") val isFavourite: Boolean
)
