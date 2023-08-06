package com.miraelDev.hikari.data.Repository

import com.miraelDev.hikari.data.mapper.Mapper
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import com.miraelDev.hikari.data.remote.searchApi.SearchApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SearchAnimeRepositoryImpl @Inject constructor(
    private val mapper: Mapper,
    private val searchApiService: SearchApiService,
) : SearchAnimeRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    private var _filterMap = mutableMapOf<Int, String>()
    private val filterMap: Map<Int, String> get() = _filterMap.toMap()

    private val _filterListFlow = MutableStateFlow<List<String>>(listOf())

    private val searchResults = MutableStateFlow<List<AnimeInfo>>(listOf())


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
        searchResults.value =
            mapper.mapAnimeListDtoToListAnimeInfo(searchApiService.searchAnimeByName(name))
    }

    override fun getAnimeBySearch(): StateFlow<List<AnimeInfo>> = searchResults.asStateFlow()


}