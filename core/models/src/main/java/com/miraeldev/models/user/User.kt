package com.miraeldev.models.user


import com.miraeldev.anime.LastWatchedAnime
import kotlinx.serialization.Serializable


@Serializable
data class User(
    val id: Long = -1,
    val username: String = "",
    val name: String = "",
    val image: String = "",
    val password: String = "",
    val email: String = "",
    val lastWatchedAnime: LastWatchedAnime? = LastWatchedAnime(-1)
)

