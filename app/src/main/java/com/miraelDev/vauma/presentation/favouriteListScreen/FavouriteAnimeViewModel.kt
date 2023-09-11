package com.miraelDev.vauma.presentation.favouriteListScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.domain.models.AnimeInfo
import com.miraelDev.vauma.domain.usecases.searchUsecase.SaveSearchTextUseCase
import com.miraelDev.vauma.domain.usecases.searchUsecase.SearchAnimeByNameUseCase
import com.miraelDev.vauma.domain.usecases.favouriteListUseCase.GetFavouriteAnimeListUseCase
import com.miraelDev.vauma.domain.usecases.favouriteListUseCase.LoadAnimeListUseCase
import com.miraelDev.vauma.domain.usecases.favouriteListUseCase.SearchAnimeItemInDatabaseUseCase
import com.miraelDev.vauma.domain.usecases.favouriteListUseCase.SelectAnimeItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.miraelDev.vauma.domain.result.Result
import com.miraelDev.vauma.exntensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class FavouriteAnimeViewModel @Inject constructor(
    private val getFavouriteAnimeListUseCase: GetFavouriteAnimeListUseCase,
    private val selectAnimeItemUseCase: SelectAnimeItemUseCase,
    private val searchAnimeItemInDatabaseUseCase: SearchAnimeItemInDatabaseUseCase,
    private val loadAnimeListUseCase: LoadAnimeListUseCase,
    private val searchAnimeByNameUseCase: SearchAnimeByNameUseCase,
    private val saveSearchTextUseCase: SaveSearchTextUseCase,
) : ViewModel() {

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState

    private val loadingFlow = MutableSharedFlow<FavouriteListScreenState.Loading>()

    val screenState = getFavouriteAnimeListUseCase()
        .map {
            when (val res = it) {
                is Result.Failure -> {
                    FavouriteListScreenState.Failure(failure = res.failureCause) as FavouriteListScreenState
                }

                is Result.Success -> {
                    FavouriteListScreenState.Result(result = res.animeList) as FavouriteListScreenState
                }

                is Result.Initial -> {
                    FavouriteListScreenState.Loading as FavouriteListScreenState
                }
            }
        }
        .mergeWith(loadingFlow)
        .onStart { FavouriteListScreenState.Loading as FavouriteListScreenState }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            FavouriteListScreenState.Loading
        )

    fun updateSearchTextState(value: String) {
        _searchTextState.value = value
    }

    fun selectAnimeItem(animeInfo: AnimeInfo) {
        viewModelScope.launch {
            selectAnimeItemUseCase(false, animeInfo)
        }
    }

    fun searchAnimeItemInDatabase(name: String) {
        viewModelScope.launch {
            searchAnimeItemInDatabaseUseCase(name)
        }
    }

    fun loadAnimeList() {
        viewModelScope.launch {
            loadAnimeListUseCase()
        }
    }

    fun searchAnimeByName(name: String) {
        viewModelScope.launch {
            searchAnimeByNameUseCase(name)
            saveSearchTextUseCase(name)
        }
    }
}