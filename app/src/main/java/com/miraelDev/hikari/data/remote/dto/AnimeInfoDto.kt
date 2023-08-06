package com.miraelDev.hikari.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeInfoDto(

    @SerialName("id") val id : Int,

    @SerialName("name") val name : String,

    @SerialName("russian") val russianName : String,

    @SerialName("kind") val kind : String,

    @SerialName("score") val score : Float,

    @SerialName("status") val status : String,

    @SerialName("episodes") val episodes : Int,

    @SerialName("rating") val rating : String,

    @SerialName("description") val description : String,
)
