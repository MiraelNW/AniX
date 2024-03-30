package com.miraeldev.detailinfo.presentation.detailComponent

import com.miraeldev.anime.AnimeDetailInfo
import kotlinx.coroutines.flow.StateFlow

interface DetailComponent {

    val model: StateFlow<DetailStore.State>

    fun onBackClicked()
    fun loadAnimeDetail(id: Int)
    fun downloadEpisode(url: String, videoName: String)
    fun loadAnimeVideo(animeItem: AnimeDetailInfo, videoId: Int)
    fun onAnimeItemClick(id: Int)
    fun onSeriesClick()
    fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeDetailInfo)
}