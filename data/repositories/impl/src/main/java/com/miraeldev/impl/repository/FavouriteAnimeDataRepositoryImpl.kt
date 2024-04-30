package com.miraeldev.impl.repository

import com.miraeldev.api.FavouriteAnimeDao
import com.miraeldev.api.FavouriteAnimeDataRepository
import com.miraeldev.extensions.mergeWith
import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.result.FailureCauses
import com.miraeldev.result.ResultAnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class FavouriteAnimeDataRepositoryImpl(
    private val favouriteAnimeDao: FavouriteAnimeDao
) : FavouriteAnimeDataRepository {

    private val searchResults = MutableSharedFlow<ResultAnimeInfo>()

    private val _initialList = mutableListOf<AnimeInfo>()

    private val initialListFlow = MutableSharedFlow<ResultAnimeInfo>()

    private val favouriteListIsNeeded = MutableSharedFlow<Unit>(replay = 1)

    private val favouriteResult =
        favouriteAnimeDao.getFavouriteAnimeList()
            .map { list ->
                if (list.isEmpty()) {
                    ResultAnimeInfo.Failure(failureCause = FailureCauses.NotFound)
                } else {
                    _initialList.removeAll(_initialList)
                    _initialList.addAll(list)
                    ResultAnimeInfo.Success(animeList = list)
                }
            }
            .mergeWith(searchResults)
            .mergeWith(initialListFlow)

    override suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo) {

        if (isSelected) {
            favouriteAnimeDao.insertFavouriteAnimeItem(animeInfo)
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

        val searchList = favouriteAnimeDao.searchAnimeItem(name)
        if (searchList.isEmpty()) {
            searchResults.emit(ResultAnimeInfo.Failure(failureCause = FailureCauses.NotFound))
        } else {
            searchResults.emit(ResultAnimeInfo.Success(animeList = searchList))
        }
    }
}