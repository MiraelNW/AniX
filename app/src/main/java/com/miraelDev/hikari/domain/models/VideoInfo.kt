package com.miraelDev.hikari.domain.models


import androidx.compose.runtime.Stable
import androidx.media3.common.MediaItem


@Stable
data class VideoInfo(
        val id: Int = 0,
        val imageUrl: String = "",
        val playerUrl: List<String> = listOf(
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
        ),
        val videoName: List<String> = listOf(
                "First video",
                "Second video",
                "Third video",
                "Fourth video",
                "Five video",
                "Six video",
                "Sev video",
                "eight video",
                "nine video",
                "ten video",
                "elv video",
        ),
)
