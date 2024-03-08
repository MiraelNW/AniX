package com.miraeldev.data.repository

import com.miraeldev.FavouriteAnimeDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.anime.toAnimeInfo
import com.miraeldev.data.local.dao.FavouriteAnimeDao
import com.miraeldev.data.local.models.favourite.toAnimeInfo
import com.miraeldev.data.mapper.AnimeModelsMapper
import com.miraeldev.extensions.mergeWith
import com.miraeldev.result.FailureCauses
import com.miraeldev.result.ResultAnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class FavouriteAnimeDataRepositoryImpl(
    private val favouriteAnimeDao: FavouriteAnimeDao,
    private val animeModelsMapper: AnimeModelsMapper
) : FavouriteAnimeDataRepository {

    private val searchResults = MutableSharedFlow<ResultAnimeInfo>()

    private val _initialList = mutableListOf<AnimeInfo>()

    private val initialListFlow = MutableSharedFlow<ResultAnimeInfo>()

    private val favouriteListIsNeeded = MutableSharedFlow<Unit>(replay = 1)

    private val favouriteResult =
        favouriteAnimeDao.getFavouriteAnimeList()
            .map { list ->
                val animeInfoList = list.map { it.toAnimeInfo() }
                if (list.isEmpty()) {
                    ResultAnimeInfo.Failure(failureCause = FailureCauses.NotFound)
                } else {
                    _initialList.removeAll(_initialList)
                    _initialList.addAll(animeInfoList)
                    ResultAnimeInfo.Success(animeList = animeInfoList)
                }
            }
            .mergeWith(searchResults)
            .mergeWith(initialListFlow)


    override suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo) {

        if (isSelected) {
            favouriteAnimeDao.insertFavouriteAnimeItem(
                animeModelsMapper.mapAnimeInfoToAnimeDbModel(animeInfo)
            )
        } else {
            favouriteAnimeDao.deleteFavouriteAnimeItem(animeInfo.id)
        }

        favouriteListIsNeeded.emit(Unit)
    }

    override suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: LastWatchedAnime) {

        if (isSelected) {
            favouriteAnimeDao.insertFavouriteAnimeItem(
                animeModelsMapper.mapAnimeInfoToAnimeDbModel(animeInfo.toAnimeInfo())
            )
        } else {
            favouriteAnimeDao.deleteFavouriteAnimeItem(animeInfo.id)
        }

        favouriteListIsNeeded.emit(Unit)
    }

    override fun getFavouriteAnimeList(): Flow<ResultAnimeInfo> = favouriteResult

    override suspend fun loadAnimeList() {
        if (_initialList.isEmpty()) {
            initialListFlow.emit(ResultAnimeInfo.Failure(failureCause = FailureCauses.NotFound))
        } else {
            initialListFlow.emit(ResultAnimeInfo.Success(animeList = _initialList.toList()))
        }
    }

    override suspend fun searchAnimeItemInDatabase(name: String) {

        val searchList = favouriteAnimeDao.searchAnimeItem(name).map { it.toAnimeInfo() }
        if (searchList.isEmpty()) {
            searchResults.emit(ResultAnimeInfo.Failure(failureCause = FailureCauses.NotFound))
        } else {
            searchResults.emit(ResultAnimeInfo.Success(animeList = searchList))
        }
    }


}