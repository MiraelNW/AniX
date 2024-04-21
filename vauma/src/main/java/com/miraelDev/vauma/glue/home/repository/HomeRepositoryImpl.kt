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
import com.miraeldev.models.paging.PagingState
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
    override fun getPagingNewAnimeList(): Flow<PagingState> {
        return animeListDataRepository.getPagingNewAnimeList()
    }

    override fun getPagingPopularAnimeList(): Flow<PagingState> {
        return animeListDataRepository.getPagingPopularAnimeList()
    }

    override fun getPagingNameAnimeList(): Flow<PagingState> {
        return animeListDataRepository.getPagingNameAnimeList()
    }

    override fun getPagingFilmsAnimeList(): Flow<PagingState> {
        return animeListDataRepository.getPagingFilmsAnimeList()
    }

    override suspend fun loadNewCategoryNextPage() {
        return animeListDataRepository.loadNewCategoryNextPage()
    }

    override suspend fun loadPopularCategoryNextPage() {
        return animeListDataRepository.loadPopularCategoryNextPage()
    }

    override suspend fun loadNameCategoryNextPage() {
        return animeListDataRepository.loadNameCategoryNextPage()
    }

    override suspend fun loadFilmCategoryNextPage() {
        return animeListDataRepository.loadFilmCategoryNextPage()
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