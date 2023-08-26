package com.miraelDev.hikari.data.Repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.miraelDev.hikari.data.remote.categoriesLists.FilmsCategoryPagingDataStore
import com.miraelDev.hikari.data.remote.categoriesLists.NameCategoryPagingDataStore
import com.miraelDev.hikari.data.remote.categoriesLists.NewCategoryPagingDataStore
import com.miraelDev.hikari.data.remote.categoriesLists.PopularCategoryPagingDataStore
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.AnimeListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AnimeListRepositoryImpl @Inject constructor(
    private val newCategoryPagingDataStore: NewCategoryPagingDataStore,
    private val nameCategoryPagingDataStore: NameCategoryPagingDataStore,
    private val filmsCategoryPagingDataStore: FilmsCategoryPagingDataStore,
    private val popularCategoryPagingDataStore: PopularCategoryPagingDataStore,
) : AnimeListRepository {

    override fun getNewAnimeList(): Flow<PagingData<AnimeInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { newCategoryPagingDataStore }
        ).flow

    }


    override fun getPopularAnimeList(): Flow<PagingData<AnimeInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { popularCategoryPagingDataStore }
        ).flow

    }

    override fun getNameAnimeList(): Flow<PagingData<AnimeInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { nameCategoryPagingDataStore }
        ).flow

    }

    override fun getFilmsAnimeList(): Flow<PagingData<AnimeInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { filmsCategoryPagingDataStore }
        ).flow

    }

}