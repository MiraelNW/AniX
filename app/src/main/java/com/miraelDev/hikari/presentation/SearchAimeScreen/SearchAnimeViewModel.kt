package com.miraelDev.hikari.presentation.SearchAimeScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miraelDev.hikari.domain.usecases.searchUsecase.GetSearchHistoryListUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.GetSearchNameUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.SaveNameInAnimeSearchHistoryUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.SearchAnimeByNameUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.filterUsecase.GetFilterListUseCase
import com.miraelDev.hikari.exntensions.mergeWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAnimeViewModel @Inject constructor(
    private val getFilterListUseCase: GetFilterListUseCase,

    private val searchAnimeByNameUseCase: SearchAnimeByNameUseCase,

    private val saveNameInAnimeSearchHistoryUseCase: SaveNameInAnimeSearchHistoryUseCase,
    private val getSearchHistoryListUseCase: GetSearchHistoryListUseCase,

    private val getSearchNameUseCase: GetSearchNameUseCase
) : ViewModel() {


    private val filterList = getFilterListUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            listOf()
        )

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState

    private val showStartAnimationFlow = MutableSharedFlow<SearchAnimeScreenState>()

    private val showSearchHistoryFlow = MutableSharedFlow<SearchAnimeScreenState>()

    private val searchResult =
        MutableSharedFlow<SearchAnimeScreenState>()

    val searchHistory = getSearchHistoryListUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            listOf()
        )

    val screenState = searchResult
        .onStart {
            emit(
                SearchAnimeScreenState.InitialList(
                    result = searchAnimeByNameUseCase(
                        INITIAL_REQUEST
                    )
                        .cachedIn(viewModelScope)
                )
            )
        }
        .mergeWith(showStartAnimationFlow)
        .mergeWith(showSearchHistoryFlow)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            SearchAnimeScreenState.EmptyList
        )

    init {
        getSearchName()
    }

    fun updateSearchTextState(value: String) {
        _searchTextState.value = value
    }

    private fun getSearchName() {
        viewModelScope.launch {
            getSearchNameUseCase().collect { name ->
                updateSearchTextState(name)
            }
        }
    }

    fun searchAnimeByName(name: String) {
        viewModelScope.launch {

            searchResult.emit(
                SearchAnimeScreenState.SearchResult(
                    result = searchAnimeByNameUseCase(name).cachedIn(viewModelScope)
                )
            )

            saveNameInAnimeSearchHistoryUseCase(name)
        }
    }

    fun showSearchHistory() {
        viewModelScope.launch {
            showSearchHistoryFlow.emit(SearchAnimeScreenState.SearchHistory(filterList.value))
        }
    }

    fun showStartAnimation() {
        viewModelScope.launch {
            searchResult.emit(
                SearchAnimeScreenState.InitialList(
                    result = searchAnimeByNameUseCase(INITIAL_REQUEST).cachedIn(viewModelScope)
                )
            )
        }
    }

    companion object {
        private const val INITIAL_REQUEST = ""
    }
}