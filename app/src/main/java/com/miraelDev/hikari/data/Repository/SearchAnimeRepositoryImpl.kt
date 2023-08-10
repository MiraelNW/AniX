package com.miraelDev.hikari.data.Repository

import android.util.Log
import com.miraelDev.hikari.data.local.Dao.SearchAnimeDaoImpl
import com.miraelDev.hikari.domain.result.Result
import com.miraelDev.hikari.data.mapper.Mapper
import com.miraelDev.hikari.data.remote.searchApi.ApiResult
import com.miraelDev.hikari.data.remote.searchApi.SearchApiService
import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SearchAnimeRepositoryImpl @Inject constructor(
        private val mapper: Mapper,
        private val searchApiService: SearchApiService,
        private val searchAnimeDaoImpl: SearchAnimeDaoImpl
) : SearchAnimeRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    private var _filterMap = mutableMapOf<Int, String>()
    private val filterMap: Map<Int, String> get() = _filterMap.toMap()

    private val _filterListFlow = MutableStateFlow<List<String>>(listOf())

    private val searchResults = MutableSharedFlow<Result>()


    override fun getFilterList(): StateFlow<List<String>> = _filterListFlow.asStateFlow()

    override suspend fun addToFilterList(categoryId: Int, category: String) {
        _filterMap[categoryId] = category
        val filterList = mutableListOf<String>().apply {
            addAll(filterMap.values)
        }
        _filterListFlow.emit(filterList)
    }

    override suspend fun removeFromFilterList(categoryId: Int, category: String) {
        _filterMap.remove(categoryId, category)
        val filterList = mutableListOf<String>().apply {
            addAll(filterMap.values)
        }
        _filterListFlow.emit(filterList)
    }

    override suspend fun clearAllFilters() {
        _filterMap = mutableMapOf<Int, String>()
        _filterListFlow.value = listOf()
    }

    override suspend fun searchAnimeByName(name: String) {
        delay(2000)
        when (val apiResult = searchApiService.searchAnimeByName(name)) {
            is ApiResult.Success -> {
                searchResults.emit(
                        Result.Success(animeList = mapper.mapAnimeListDtoToListAnimeInfo(apiResult.animeList))
                )
            }

            is ApiResult.Failure -> {
                searchResults.emit(
                        Result.Failure(failureCause = apiResult.failureCause)
                )

            }
        }
    }

    override suspend fun saveNameInAnimeSearchHistory(name: String) =
            searchAnimeDaoImpl.insertSearchItem(name)

    override fun getSearchHistoryList(): Flow<List<String>> = searchAnimeDaoImpl.getSearchHistoryList()

    override fun getAnimeBySearch(): SharedFlow<Result> = searchResults.asSharedFlow()


}