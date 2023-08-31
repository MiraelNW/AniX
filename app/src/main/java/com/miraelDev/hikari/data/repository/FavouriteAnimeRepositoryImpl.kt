package com.miraelDev.hikari.data.repository

import com.miraelDev.hikari.data.local.dao.FavouriteAnimeDao
import com.miraelDev.hikari.data.local.models.favourite.AnimeInfoDbModel
import com.miraelDev.hikari.data.mapper.Mapper
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.FavouriteAnimeRepository
import com.miraelDev.hikari.domain.result.Result
import com.miraelDev.hikari.exntensions.mergeWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavouriteAnimeRepositoryImpl @Inject constructor(
    private val mapper: Mapper,
    private val favouriteAnimeDao: FavouriteAnimeDao
) : FavouriteAnimeRepository {

    private var searchResults = MutableSharedFlow<Result>()
    private var favouriteListIsNeeded = MutableSharedFlow<Unit>(replay = 1)

    private var nextFrom: Int? = null

    private val _animeList = mutableListOf<AnimeInfoDbModel>()
    private val animeList: List<AnimeInfoDbModel> get() = _animeList.toList()

    private val favouriteResult = flow {
        favouriteListIsNeeded.emit(Unit)
        favouriteListIsNeeded.collect {
//            val startFrom = nextFrom
//            if (startFrom == null && animeList.isNotEmpty()) {
//                emit(mapper.mapAnimeInfoDbModelListIntoResult(animeList))
//                return@collect
//            }
//            _animeList.removeAll(animeList)
//            _animeList.addAll(
//                if (startFrom == null) {
//                    favouriteAnimeDao.getFavouriteAnimeList()
//                } else {
//                    favouriteAnimeDao.getFavouriteAnimeList(startFrom)
//                }
//            )
//            nextFrom = animeList.size
            emit(mapper.mapAnimeInfoDbModelListIntoResult(favouriteAnimeDao.getFavouriteAnimeList()))
        }
    }
        .mergeWith(searchResults)

    override suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo) {

        if (isSelected) {
            favouriteAnimeDao.insertFavouriteAnimeItem(
                mapper.mapAnimeInfoToFavouriteAnimeDbModel(animeInfo)
            )
        } else {
            favouriteAnimeDao.deleteFavouriteAnimeItem(animeInfo.id)
        }

        favouriteListIsNeeded.emit(Unit)
    }

    override fun getFavouriteAnimeList(): Flow<Result> = favouriteResult

    override suspend fun loadAnimeList() {
        favouriteListIsNeeded.emit(Unit)
    }

    override suspend fun searchAnimeItemInDatabase(name: String) {

        searchResults.emit(
            mapper.mapAnimeInfoDbModelListIntoResult(favouriteAnimeDao.searchAnimeItem(name))
        )
    }

}