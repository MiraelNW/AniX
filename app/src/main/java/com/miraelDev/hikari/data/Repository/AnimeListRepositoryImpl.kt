package com.miraelDev.hikari.data.Repository

import com.miraelDev.hikari.data.mapper.Mapper
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.AnimeListRepository
import com.miraelDev.hikari.entensions.mergeWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    private val initialAnimeList = flow { emit(newCategoryList) }
    private val animeListByCategory = MutableSharedFlow<List<AnimeInfo>>()

    private val animeList = initialAnimeList
        .mergeWith(animeListByCategory)
        .stateIn(
            scope,
            SharingStarted.Lazily,
            newCategoryList
        )

    override fun getAnimeListByCategory(category: Int) {
        val list = when (category) {
            0 -> {
                newCategoryList
            }
            1 -> {
                popularCategoryList
            }
            2 -> {
                nameCategoryList
            }
            3 -> {
                filmsCategoryList
            }
            else -> {
                newCategoryList
            }
        }
        scope.launch {
            animeListByCategory.emit(list)
        }
    }

    override fun getAnimeList() = animeList

}