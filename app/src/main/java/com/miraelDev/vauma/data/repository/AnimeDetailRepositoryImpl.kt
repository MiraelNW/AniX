package com.miraelDev.vauma.data.repository

import com.miraelDev.vauma.data.local.dao.FavouriteAnimeDao
import com.miraelDev.vauma.data.remote.ApiResult
import com.miraelDev.vauma.data.remote.dto.toAnimeDetailInfo
import com.miraelDev.vauma.data.remote.searchApi.SearchApiService
import com.miraelDev.vauma.domain.models.AnimeDetailInfo
import com.miraelDev.vauma.domain.models.AnimeInfo
import com.miraelDev.vauma.domain.models.toAnimeDbModel
import com.miraelDev.vauma.domain.models.toAnimeInfo
import com.miraelDev.vauma.domain.repository.AnimeDetailRepository
import com.miraelDev.vauma.domain.repository.VideoPlayerRepository
import com.miraelDev.vauma.domain.result.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AnimeDetailRepositoryImpl @Inject constructor(
    private val searchApiService: SearchApiService,
    private val videoPlayerRepository: VideoPlayerRepository,
    private val favouriteAnimeDao: FavouriteAnimeDao
) : AnimeDetailRepository {

    private val _animeDetail = MutableSharedFlow<Result<AnimeDetailInfo>>()

    override fun getAnimeDetail(): SharedFlow<Result<AnimeDetailInfo>> = _animeDetail.asSharedFlow()

    override suspend fun loadAnimeDetail(animeId: Int) {

        when (val apiResult = searchApiService.getAnimeById(animeId)) {
            is ApiResult.Success -> {
                val animeList = apiResult.animeList.map { it.toAnimeDetailInfo() }
                videoPlayerRepository.loadVideoPlayer(animeList.first().toAnimeInfo())
                _animeDetail.emit(
                    Result.Success(animeList = animeList)
                )
            }

            is ApiResult.Failure -> {
                _animeDetail.emit(
                    Result.Failure(failureCause = apiResult.failureCause)
                )

            }
        }
    }

    override suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo) {
        if (isSelected) {
            favouriteAnimeDao.insertFavouriteAnimeItem(
                animeInfo.toAnimeDbModel()
            )
        } else {
            favouriteAnimeDao.deleteFavouriteAnimeItem(animeInfo.id)
        }
    }


}