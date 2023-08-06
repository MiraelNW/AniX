package com.miraelDev.hikari.data.Repository

import com.miraelDev.hikari.data.mapper.Mapper
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.AnimeListRepository
import com.miraelDev.hikari.exntensions.mergeWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnimeListRepositoryImpl @Inject constructor(
    mapper: Mapper
) : AnimeListRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val newCategoryList = listOf(
        AnimeInfo(0),
        AnimeInfo(1),
    )
    private val popularCategoryList = listOf(
        AnimeInfo(0), AnimeInfo(1), AnimeInfo(2)
    )
    private val nameCategoryList = listOf(
        AnimeInfo(0), AnimeInfo(1), AnimeInfo(2), AnimeInfo(3),
    )
    private val filmsCategoryList = listOf(
        AnimeInfo(0),
        AnimeInfo(1),
        AnimeInfo(2),
        AnimeInfo(3),
        AnimeInfo(4),
    )

    private val newAnimeList = flowOf(newCategoryList)

    private val popularAnimeList = flowOf(popularCategoryList)

    private val nameAnimeList = flowOf(nameCategoryList)

    private val filmsAnimeList = flowOf(filmsCategoryList)

    override fun getNewAnimeList() = newAnimeList

    override fun getPopularAnimeList(): Flow<List<AnimeInfo>> = popularAnimeList

    override fun getNameAnimeList(): Flow<List<AnimeInfo>> = nameAnimeList

    override fun getFilmsAnimeList(): Flow<List<AnimeInfo>> = filmsAnimeList

}