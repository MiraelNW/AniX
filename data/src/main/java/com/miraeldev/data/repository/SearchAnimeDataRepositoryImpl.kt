package com.miraeldev.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.miraeldev.SearchAnimeDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.local.AppDatabase
import com.miraeldev.data.local.dao.SearchHistoryAnimeDao
import com.miraeldev.data.local.models.SearchHistoryDbModel
import com.miraeldev.data.remote.NetworkHandler
import com.miraeldev.data.remote.searchApi.SearchPagingPagingSource
import com.miraeldev.data.remoteMediator.InitialSearchRemoteMediator
import com.miraeldev.di.qualifiers.CommonHttpClient
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
internal class SearchAnimeDataRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    @CommonHttpClient private val client: HttpClient,
    private val networkHandler: NetworkHandler,
    private val searchAnimeDao: SearchHistoryAnimeDao,
    private val localTokenService: LocalTokenService
) : SearchAnimeDataRepository {

    private var _filterMap = mutableMapOf<Int, String>()
    private val filterMap: Map<Int, String> get() = _filterMap.toMap()

    private val _filterListFlow = MutableSharedFlow<List<String>>(replay = 1)

    private val _searchTextFlow = MutableStateFlow("")

    private val searchInitialList = MutableSharedFlow<Flow<PagingData<AnimeInfo>>>()

    private val _searchName = MutableStateFlow<String>("")

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

        _searchName.value = name
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
                SearchPagingPagingSource(
                    name = name,
                    yearFilter = yearFilter,
                    sortFilter = sortFilter,
                    genreListFilter = genreListFilter,
                    client = client,
                    networkHandler = networkHandler,
                    localTokenService = localTokenService
                )
            }
        ).flow
    }

    override fun getSearchResults(): Flow<Flow<PagingData<AnimeInfo>>> = emptyFlow()

    override fun getSearchInitialList(): Flow<Flow<PagingData<AnimeInfo>>> = searchInitialList.asSharedFlow()


    override fun getSearchName(): Flow<String> = _searchTextFlow.asStateFlow()

    override suspend fun saveNameInAnimeSearchHistory(name: String) {

        if (name.isEmpty()) return

        val searchHistoryDbModel = SearchHistoryDbModel(name)

        val searchList = searchAnimeDao.getSearchHistoryList()

        val mutableSearchList = ArrayDeque(searchList)
        mutableSearchList.remove(searchHistoryDbModel)

        if (searchList.size == 20) {
            mutableSearchList.addFirst(searchHistoryDbModel)
            mutableSearchList.removeLast()
        } else {
            mutableSearchList.addFirst(searchHistoryDbModel)
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
            .map {
                it.map { model ->
                    model.searchHistoryItem
                }

            }


    override suspend fun loadInitialList() {
        searchInitialList.emit(
            Pager(
                config = PagingConfig(
                    pageSize = 12,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { appDatabase.initialSearchDao().getAnime() },
                remoteMediator = InitialSearchRemoteMediator(
                    client = client,
                    appDatabase = appDatabase,
                    networkHandler = networkHandler,
                    localTokenService = localTokenService
                )
            )
                .flow
        )
    }
}