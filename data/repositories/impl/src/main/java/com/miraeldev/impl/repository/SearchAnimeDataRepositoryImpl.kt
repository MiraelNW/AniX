package com.miraeldev.impl.repository

import com.miraeldev.api.AppNetworkClient
import com.miraeldev.api.SearchAnimeDataRepository
import com.miraeldev.data.remote.NetworkHandler
import com.miraeldev.impl.mapper.mapToPagingModel
import com.miraeldev.impl.pagingController.SearchResultsPagingController
import com.miraeldev.impl.pagingController.PagingController
import com.miraeldev.local.dao.searchHistoryDao.SearchHistoryAnimeDao
import com.miraeldev.local.dao.initialSearch.api.InitialSearchPagingDao
import com.miraeldev.models.dto.Response
import com.miraeldev.models.paging.PagingState
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class SearchAnimeDataRepositoryImpl(
    private val initialSearchPagingDao: InitialSearchPagingDao,
    private val networkHandler: NetworkHandler,
    private val searchAnimeDao: SearchHistoryAnimeDao,
    private val appNetworkClient: AppNetworkClient
) : SearchAnimeDataRepository {

    private var _filterMap = mutableMapOf<Int, String>()
    private val filterMap: Map<Int, String> get() = _filterMap.toMap()

    private val _filterListFlow = MutableSharedFlow<List<String>>(replay = 1)

    private val _searchTextFlow = MutableStateFlow("")

    private val _searchName = MutableStateFlow("")
    private var searchResultPagingController: SearchResultsPagingController? = null

    private val initialSearchPagingController = PagingController(
        pagingRequest = {
            appNetworkClient.getInitialListCategoryList(it).body<Response>().mapToPagingModel()
        },
        lastNodeInDb = { initialSearchPagingDao.getLastNode() },
        getAnimeListByPage = { initialSearchPagingDao.getAnimeByPage(it) },
        cashSuccessNetworkResult = { animeList, page, isLast ->
            if (page == 0L) initialSearchPagingDao.clearAllAnime()
            initialSearchPagingDao.insertAll(
                anime = animeList,
                insertTime = System.currentTimeMillis(),
                page = page,
                isLast = isLast
            )
        },
        currentTime = System.currentTimeMillis()
    )

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

    override suspend fun searchAnimeByName(name: String): Flow<PagingState> {

        val yearFilter = filterMap[1]
        val sortFilter = filterMap[2]
        val genreListFilter = mutableListOf<String>()

        _searchName.value = name
        filterMap.keys
            .filter { it != 1 && it != 2 }
            .forEach { filterMap[it]?.let { str -> genreListFilter.add(str) } }

        searchResultPagingController = SearchResultsPagingController(
            name = name,
            yearFilter = yearFilter,
            sortFilter = sortFilter,
            genreListFilter = genreListFilter,
            pagingRequest = { animeName, yearCode, sortCode, genreCode, page, pageSize ->
                appNetworkClient.getPagingFilteredList(
                    animeName,
                    yearCode,
                    sortCode,
                    genreCode,
                    page,
                    pageSize
                ).body<Response>().mapToPagingModel()
            }
        )
        return searchResultPagingController?.flow ?: emptyFlow()
    }

    override suspend fun loadSearchResultsNextPage() {
        searchResultPagingController?.loadNextPage()
    }

    override fun getSearchInitialList(): Flow<PagingState> = initialSearchPagingController.flow

    override suspend fun loadNextPage() {
        initialSearchPagingController.loadNextPage()
    }

    override fun getSearchName(): Flow<String> = _searchTextFlow.asStateFlow()

    override suspend fun saveNameInAnimeSearchHistory(name: String) {

        if (name.isEmpty()) return

        val searchList = searchAnimeDao.getSearchHistoryList()

        val mutableSearchList = ArrayDeque(searchList)
        mutableSearchList.remove(name)

        if (searchList.size == 20) {
            mutableSearchList.addFirst(name)
            mutableSearchList.removeLast()
        } else {
            mutableSearchList.addFirst(name)
        }

        mutableSearchList.forEach {
            searchAnimeDao.insertSearchItem(it)
        }
    }

    override fun saveSearchText(searchText: String) {
        _searchTextFlow.value = searchText
    }

    override fun getSearchHistoryListFlow(): Flow<List<String>> =
        searchAnimeDao.getSearchHistoryListFlow()
}