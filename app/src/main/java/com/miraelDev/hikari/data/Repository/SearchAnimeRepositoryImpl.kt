package com.miraelDev.hikari.data.Repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.miraelDev.hikari.data.local.Dao.SearchAnimeDao
import com.miraelDev.hikari.data.remote.NetworkHandler
import com.miraelDev.hikari.data.remote.searchApi.SearchPagingDataStore
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SearchAnimeRepositoryImpl @Inject constructor(
    private val client: HttpClient,
    private val networkHandler: NetworkHandler,
    private val searchAnimeDao: SearchAnimeDao
) : SearchAnimeRepository {

    private var _filterMap = mutableMapOf<Int, String>()
    private val filterMap: Map<Int, String> get() = _filterMap.toMap()

    private val _filterListFlow = MutableSharedFlow<List<String>>(replay = 1)

    private val _searchTextFlow = MutableStateFlow("")

    override fun getFilterList(): Flow<List<String>> = _filterListFlow.asSharedFlow()


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
        _filterMap = mutableMapOf()
        _filterListFlow.emit(emptyList())
    }

    override suspend fun searchAnimeByName(name: String): Flow<PagingData<AnimeInfo>> {

        val yearFilter = filterMap[1]
        val sortFilter = filterMap[2]
        val genreListFilter = mutableListOf<String>()
        filterMap.keys
            .filter { it != 1 && it != 2 }
            .forEach { filterMap[it]?.let { str -> genreListFilter.add(str) } }


        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = true
            ),
            pagingSourceFactory =
            {
                SearchPagingDataStore(
                    name = name,
                    yearFilter = yearFilter,
                    sortFilter =sortFilter,
                    genreListFilter = genreListFilter,
                    client = client,
                    networkHandler = networkHandler
                )
            }
        ).flow
    }

    override fun getSearchName(): Flow<String> = _searchTextFlow.asStateFlow()

    override suspend fun saveNameInAnimeSearchHistory(name: String) =
        searchAnimeDao.insertSearchItem(name)

    override fun saveSearchText(searchText: String) {
        _searchTextFlow.value = (searchText)
    }

    override fun getSearchHistoryList(): Flow<List<String>> = searchAnimeDao.getSearchHistoryList()

}