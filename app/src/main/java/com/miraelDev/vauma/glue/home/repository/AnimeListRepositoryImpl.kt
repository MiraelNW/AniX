package com.miraelDev.vauma.glue.home.repository

import androidx.paging.PagingData
import com.miraeldev.AnimeListDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.animelist.data.AnimeListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeListRepositoryImpl @Inject constructor(
    private val animeListDataRepository: AnimeListDataRepository
) : AnimeListRepository {
    override fun getNewAnimeList(): Flow<PagingData<AnimeInfo>> {
        return animeListDataRepository.getNewAnimeList()
    }

    override fun getPopularAnimeList(): Flow<PagingData<AnimeInfo>> {
        return animeListDataRepository.getPopularAnimeList()
    }

    override fun getNameAnimeList(): Flow<PagingData<AnimeInfo>> {
        return animeListDataRepository.getNameAnimeList()
    }

    override fun getFilmsAnimeList(): Flow<PagingData<AnimeInfo>> {
        return animeListDataRepository.getFilmsAnimeList()
    }
}