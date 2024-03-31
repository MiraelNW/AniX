package com.miraelDev.vauma.glue.favourites.repository

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.api.FavouriteAnimeDataRepository
import com.miraeldev.api.SearchAnimeDataRepository
import com.miraeldev.favourites.data.FavouriteAnimeRepository
import com.miraeldev.result.ResultAnimeInfo
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class FavouriteAnimeRepositoryImpl(
    private val favouriteAnimeDataRepository: FavouriteAnimeDataRepository,
    private val searchAnimeDataRepository: SearchAnimeDataRepository
) : FavouriteAnimeRepository {
    override suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo) {
        favouriteAnimeDataRepository.selectAnimeItem(isSelected, animeInfo)
    }

    override fun getFavouriteAnimeList(): Flow<ResultAnimeInfo> {
        return favouriteAnimeDataRepository.getFavouriteAnimeList()
    }

    override suspend fun loadAnimeList() {
        favouriteAnimeDataRepository.loadAnimeList()
    }

    override suspend fun searchAnimeItemInDatabase(name: String) {
        favouriteAnimeDataRepository.searchAnimeItemInDatabase(name)
    }

    override suspend fun searchAnimeByName(name: String) {
        searchAnimeDataRepository.searchAnimeByName(name)
    }

    override fun saveSearchText(searchText: String) {
        searchAnimeDataRepository.saveSearchText(searchText)
    }

}