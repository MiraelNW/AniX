package com.miraelDev.vauma.glue.search.repositories

import androidx.paging.PagingData
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.api.SearchAnimeDataRepository
import com.miraeldev.models.paging.PagingState
import com.miraeldev.search.data.repository.SearchAnimeRepository
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class SearchRepositoryImpl(
    private val searchAnimeDataRepository: SearchAnimeDataRepository
) : SearchAnimeRepository {
    override fun getFilterList(): Flow<List<String>> {
        return searchAnimeDataRepository.getFilterList()
    }

    override suspend fun addToFilterList(categoryId: Int, category: String) {
        searchAnimeDataRepository.addToFilterList(categoryId, category)
    }

    override suspend fun removeFromFilterList(categoryId: Int, category: String) {
        searchAnimeDataRepository.removeFromFilterList(categoryId, category)
    }

    override suspend fun clearAllFilters() {
        searchAnimeDataRepository.clearAllFilters()
    }

    override suspend fun searchAnimeByName(name: String): Flow<PagingState> {
        return searchAnimeDataRepository.searchAnimeByName(name)
    }

    override suspend fun loadSearchResultsNextPage() {
        searchAnimeDataRepository.loadSearchResultsNextPage()
    }

    override fun saveSearchText(searchText: String) {
        searchAnimeDataRepository.saveSearchText(searchText)
    }

    override suspend fun loadNextPage() {
        searchAnimeDataRepository.loadNextPage()
    }

    override fun getSearchInitialList(): Flow<PagingState> {
        return searchAnimeDataRepository.getSearchInitialList()
    }

    override fun getSearchName(): Flow<String> {
        return searchAnimeDataRepository.getSearchName()
    }

    override suspend fun saveNameInAnimeSearchHistory(name: String) {
        searchAnimeDataRepository.saveNameInAnimeSearchHistory(name)
    }

    override fun getSearchHistoryListFlow(): Flow<List<String>> {
        return searchAnimeDataRepository.getSearchHistoryListFlow()
    }
}