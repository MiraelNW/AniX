package com.miraelDev.vauma.glue.home.repository

import androidx.paging.PagingData
import com.miraeldev.AnimeListDataRepository
import com.miraeldev.FavouriteAnimeDataRepository
import com.miraeldev.HomeDataRepository
import com.miraeldev.VideoPlayerDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.animelist.data.HomeRepository
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.user.User
import com.miraeldev.anime.toAnimeInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val animeListDataRepository: AnimeListDataRepository,
    private val favouriteAnimeDataRepository: FavouriteAnimeDataRepository,
    private val homeDataRepository: HomeDataRepository,
    private val videoPlayerDataRepository: VideoPlayerDataRepository
) : HomeRepository {
    override fun getPagingNewAnimeList(): Flow<PagingData<AnimeInfo>> {
        return animeListDataRepository.getPagingNewAnimeList()
    }

    override fun getPagingPopularAnimeList(): Flow<PagingData<AnimeInfo>> {
        return animeListDataRepository.getPagingPopularAnimeList()
    }

    override fun getPagingNameAnimeList(): Flow<PagingData<AnimeInfo>> {
        return animeListDataRepository.getPagingNameAnimeList()
    }

    override fun getPagingFilmsAnimeList(): Flow<PagingData<AnimeInfo>> {
        return animeListDataRepository.getPagingFilmsAnimeList()
    }

    override suspend fun loadData() {
        homeDataRepository.loadData()
    }

    override fun getNewAnimeList(): Flow<List<AnimeInfo>> {
        return homeDataRepository.getNewAnimeList()
    }

    override fun getPopularAnimeList(): Flow<List<AnimeInfo>> {
        return homeDataRepository.getPopularAnimeList()
    }

    override fun getNameAnimeList(): Flow<List<AnimeInfo>> {
        return homeDataRepository.getNameAnimeList()
    }

    override fun getFilmsAnimeList(): Flow<List<AnimeInfo>> {
        return homeDataRepository.getFilmsAnimeList()
    }

    override suspend fun addAnimeToList(isSelected: Boolean, animeInfo: LastWatchedAnime) {
        favouriteAnimeDataRepository.selectAnimeItem(isSelected, animeInfo)
    }

    override fun getUserInfo(): Flow<User> {
        return homeDataRepository.getUserInfo()
    }

    override fun loadVideoId(animeItem: LastWatchedAnime) {
        videoPlayerDataRepository.loadVideoId(animeItem.toAnimeInfo(), animeItem.episodeNumber)
    }
}