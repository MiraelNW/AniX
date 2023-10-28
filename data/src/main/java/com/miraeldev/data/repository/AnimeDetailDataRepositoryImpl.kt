package com.miraeldev.data.repository

import com.miraeldev.AnimeDetailDataRepository
import com.miraeldev.VideoPlayerDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.anime.toAnimeInfo
import com.miraeldev.data.local.dao.FavouriteAnimeDao
import com.miraeldev.data.mapper.AnimeModelsMapper
import com.miraeldev.data.remote.ApiResult
import com.miraeldev.data.remote.dto.toAnimeDetailInfo
import com.miraeldev.data.remote.searchApi.SearchApiService
import com.miraeldev.result.ResultAnimeDetail
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

internal class AnimeDetailDataRepositoryImpl @Inject constructor(
    private val searchApiService: SearchApiService,
    private val videoPlayerDataRepository: VideoPlayerDataRepository,
    private val favouriteAnimeDao: FavouriteAnimeDao,
    private val animeModelsMapper: AnimeModelsMapper
) : AnimeDetailDataRepository {

    private val _animeDetail = MutableSharedFlow<ResultAnimeDetail>()

    override fun getAnimeDetail(): SharedFlow<ResultAnimeDetail> = _animeDetail.asSharedFlow()

    override suspend fun loadAnimeDetail(animeId: Int) {

        when (val apiResult = searchApiService.getAnimeById(animeId)) {
            is ApiResult.Success -> {
                val animeList = apiResult.animeList.map { it.toAnimeDetailInfo() }
                videoPlayerDataRepository.loadVideoPlayer(animeList.first().toAnimeInfo())
                _animeDetail.emit(
                    ResultAnimeDetail.Success(animeList = animeList)
                )
            }

            is ApiResult.Failure -> {
                _animeDetail.emit(
                    ResultAnimeDetail.Failure(failureCause = apiResult.failureCause)
                )

            }
        }
    }

    override suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo) {
        if (isSelected) {
            favouriteAnimeDao.insertFavouriteAnimeItem(
                animeModelsMapper.mapAnimeInfoToAnimeDbModel(animeInfo)
            )
        } else {
            favouriteAnimeDao.deleteFavouriteAnimeItem(animeInfo.id)
        }
    }


}