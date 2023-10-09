package com.miraelDev.vauma.data.repository

import com.miraelDev.vauma.data.local.dao.FavouriteAnimeDao
import com.miraelDev.vauma.data.local.models.favourite.toAnimeInfo
import com.miraelDev.vauma.data.remote.FailureCauses
import com.miraelDev.vauma.domain.models.anime.AnimeInfo
import com.miraelDev.vauma.domain.models.anime.toAnimeDbModel
import com.miraelDev.vauma.domain.repository.FavouriteAnimeRepository
import com.miraelDev.vauma.domain.result.Result
import com.miraelDev.vauma.exntensions.mergeWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteAnimeRepositoryImpl @Inject constructor(
    private val favouriteAnimeDao: FavouriteAnimeDao
) : FavouriteAnimeRepository {

    private val searchResults = MutableSharedFlow<Result<AnimeInfo>>()

    private val _initialList = mutableListOf<AnimeInfo>()

    private val initialListFlow = MutableSharedFlow<Result<AnimeInfo>>()

    private val favouriteListIsNeeded = MutableSharedFlow<Unit>(replay = 1)

    private val favouriteResult =
        favouriteAnimeDao.getFavouriteAnimeList()
            .map { list ->
                val animeInfoList = list.map { it.toAnimeInfo() }
                if (list.isEmpty()) {
                    Result.Failure(failureCause = FailureCauses.NotFound)
                } else {
                    _initialList.removeAll(_initialList)
                    _initialList.addAll(animeInfoList)
                    Result.Success(animeList = animeInfoList)
                }
            }
            .mergeWith(searchResults)
            .mergeWith(initialListFlow)


    override suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo) {

        if (isSelected) {
            favouriteAnimeDao.insertFavouriteAnimeItem(
                animeInfo.toAnimeDbModel()
            )
        } else {
            favouriteAnimeDao.deleteFavouriteAnimeItem(animeInfo.id)
        }

        favouriteListIsNeeded.emit(Unit)
    }

    override fun getFavouriteAnimeList(): Flow<Result<AnimeInfo>> = favouriteResult

    override suspend fun loadAnimeList() {
        if (_initialList.isEmpty()) {
            initialListFlow.emit(Result.Failure(failureCause = FailureCauses.NotFound))
        } else {
            initialListFlow.emit(Result.Success(animeList = _initialList.toList()))
        }
    }

    override suspend fun searchAnimeItemInDatabase(name: String) {

        val searchList = favouriteAnimeDao.searchAnimeItem(name).map { it.toAnimeInfo() }
        if (searchList.isEmpty()) {
            searchResults.emit(Result.Failure(failureCause = FailureCauses.NotFound))
        } else {
            searchResults.emit(Result.Success(animeList = searchList))
        }
    }


}