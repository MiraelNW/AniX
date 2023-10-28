package com.miraeldev.detailinfo.data.repositories

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.result.ResultAnimeDetail
import kotlinx.coroutines.flow.Flow

interface AnimeDetailRepository {

    fun getAnimeDetail(): Flow<ResultAnimeDetail>
    suspend fun loadAnimeDetail(animeId: Int)

    suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeDetailInfo)

    suspend fun downloadVideo(url:String, videoName:String)

    fun loadVideoId(videoId:Int)

}