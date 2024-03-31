package com.miraelDev.vauma.glue.home.repository

import androidx.paging.PagingData
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.anime.toAnimeDetailInfo
import com.miraeldev.animelist.data.HomeRepository
import com.miraeldev.api.AnimeListDataRepository
import com.miraeldev.api.FavouriteAnimeDataRepository
import com.miraeldev.api.HomeDataRepository
import com.miraeldev.api.VideoPlayerDataRepository
import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class HomeRepositoryImpl(
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

    override suspend fun loadData(): Map<Int, List<AnimeInfo>> {
        return homeDataRepository.loadData()
    }

    override suspend fun addAnimeToList(isSelected: Boolean, animeInfo: LastWatchedAnime) {
        favouriteAnimeDataRepository.selectAnimeItem(isSelected, animeInfo)
    }

    override fun getUserInfo(): Flow<User> {
        return homeDataRepository.getUserInfo()
    }

    override fun loadVideoId(animeItem: LastWatchedAnime) {
        videoPlayerDataRepository.loadVideoId(animeItem.toAnimeDetailInfo(), animeItem.episodeNumber)
    }
}