package com.miraelDev.vauma.data.repository

import com.miraelDev.vauma.data.local.dao.FavouriteAnimeDao
import com.miraelDev.vauma.data.local.models.favourite.AnimeInfoDbModel
import com.miraelDev.vauma.data.local.models.favourite.toAnimeInfo
import com.miraelDev.vauma.data.remote.FailureCauses
import com.miraelDev.vauma.domain.models.AnimeInfo
import com.miraelDev.vauma.domain.models.toAnimeDbModel
import com.miraelDev.vauma.domain.repository.FavouriteAnimeRepository
import com.miraelDev.vauma.domain.result.Result
import com.miraelDev.vauma.exntensions.mergeWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavouriteAnimeRepositoryImpl @Inject constructor(
    private val favouriteAnimeDao: FavouriteAnimeDao
) : FavouriteAnimeRepository {

    private var searchResults = MutableSharedFlow<Result<AnimeInfo>>()
    private var favouriteListIsNeeded = MutableSharedFlow<Unit>(replay = 1)

    private var nextFrom: Int? = null

    private val _animeList = mutableListOf<AnimeInfoDbModel>()
    private val animeList: List<AnimeInfoDbModel> get() = _animeList.toList()

    private val favouriteResult = flow {
        favouriteListIsNeeded.emit(Unit)
        favouriteListIsNeeded.collect {
            val favList = favouriteAnimeDao.getFavouriteAnimeList().map { it.toAnimeInfo() }
            if (favList.isEmpty()) {
                emit(Result.Failure(failureCause = FailureCauses.NotFound))
            } else {
                emit(Result.Success(animeList = favList))
            }

        }
    }
        .mergeWith(searchResults)

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
        favouriteListIsNeeded.emit(Unit)
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