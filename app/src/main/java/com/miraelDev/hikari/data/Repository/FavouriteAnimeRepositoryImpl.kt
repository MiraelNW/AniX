package com.miraelDev.hikari.data.Repository

import android.util.Log
import com.miraelDev.hikari.data.local.Dao.FavouriteAnimeDao
import com.miraelDev.hikari.data.mapper.Mapper
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.FavouriteAnimeRepository
import com.miraelDev.hikari.domain.result.Result
import com.miraelDev.hikari.exntensions.mergeWith
import com.miraelDev.hikari.presentation.FavouriteListScreen.FavouriteListScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteAnimeRepositoryImpl @Inject constructor(
        private val mapper: Mapper,
        private val favouriteAnimeDao: FavouriteAnimeDao
) : FavouriteAnimeRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    private var searchResults = MutableSharedFlow<Result>()
    private var favouriteListIsNeeded = MutableSharedFlow<Unit>(replay = 1)


    private val favouriteResult = flow {
        favouriteListIsNeeded.emit(Unit)
        favouriteListIsNeeded.collect {
            Log.d("tag","from collector" + favouriteAnimeDao.getFavouriteAnimeList().size.toString())
            val favList = mapper.mapAnimeInfoDbModelListIntoResult(favouriteAnimeDao.getFavouriteAnimeList())
            emit(favList)
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
        Log.d("tag", "emit")
        favouriteListIsNeeded.emit(Unit)
    }

    override suspend fun searchAnimeItem(name: String) {

        searchResults.emit(
                mapper.mapAnimeInfoDbModelListIntoResult(favouriteAnimeDao.searchAnimeItem(name))
        )
    }

}