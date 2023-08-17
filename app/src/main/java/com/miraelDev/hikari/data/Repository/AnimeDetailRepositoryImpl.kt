package com.miraelDev.hikari.data.Repository

import com.miraelDev.hikari.data.local.Dao.FavouriteAnimeDao
import com.miraelDev.hikari.data.mapper.Mapper
import com.miraelDev.hikari.data.remote.searchApi.ApiResult
import com.miraelDev.hikari.data.remote.searchApi.SearchApiService
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import com.miraelDev.hikari.domain.repository.VideoPlayerRepository
import com.miraelDev.hikari.domain.result.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AnimeDetailRepositoryImpl @Inject constructor(
        private val mapper: Mapper,
        private val searchApiService: SearchApiService,
        private val videoPlayerRepository: VideoPlayerRepository,
        private val favouriteAnimeDao: FavouriteAnimeDao
) : AnimeDetailRepository {

    private val _animeDetail = MutableSharedFlow<Result>()

    override fun getAnimeDetail(): SharedFlow<Result> = _animeDetail.asSharedFlow()

    override suspend fun loadAnimeDetail(animeId: Int) {
        delay(2000)

        when (val apiResult = searchApiService.getAnimeById(animeId)) {
            is ApiResult.Success -> {
                val animeList = mapper.mapAnimeListDtoToListAnimeInfo(apiResult.animeList)
                videoPlayerRepository.loadVideoPlayer(animeList.first())
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

    override suspend fun selectAnimeItem(isSelected: Boolean,animeInfo: AnimeInfo) {
        if (isSelected) {
            favouriteAnimeDao.insertFavouriteAnimeItem(
                    mapper.mapAnimeInfoToFavouriteAnimeDbModel(animeInfo)
            )
        } else {
            favouriteAnimeDao.deleteFavouriteAnimeItem(animeInfo.id)
        }
    }


}