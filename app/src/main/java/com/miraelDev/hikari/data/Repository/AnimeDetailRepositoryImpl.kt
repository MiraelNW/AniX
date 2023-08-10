package com.miraelDev.hikari.data.Repository

import com.miraelDev.hikari.data.mapper.Mapper
import com.miraelDev.hikari.data.remote.searchApi.ApiResult
import com.miraelDev.hikari.data.remote.searchApi.SearchApiService
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import com.miraelDev.hikari.domain.result.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AnimeDetailRepositoryImpl @Inject constructor(
        private val mapper: Mapper,
        private val searchApiService: SearchApiService
) : AnimeDetailRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _animeDetail = MutableSharedFlow<Result>()
    private val _videoId = MutableStateFlow(0)

    override fun getAnimeDetail(): SharedFlow<Result> = _animeDetail.asSharedFlow()

    override suspend fun loadAnimeDetail(animeId: Int) {
        delay(2000)
        when (val apiResult = searchApiService.getAnimeById(animeId)) {
            is ApiResult.Success -> {
                _animeDetail.emit(
                        Result.Success(animeList = mapper.mapAnimeListDtoToListAnimeInfo(apiResult.animeList))
                )
            }

            is ApiResult.Failure -> {
                _animeDetail.emit(
                        Result.Failure(failureCause = apiResult.failureCause)
                )

            }
        }
    }

    override fun loadVideoId(videoId: Int) {
        _videoId.value = videoId
    }

    override fun getVideoId(): StateFlow<Int> = _videoId.asStateFlow()


}