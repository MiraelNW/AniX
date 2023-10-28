package com.miraeldev.favourites.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.exntensions.mergeWith
import com.miraeldev.favourites.domain.useCases.GetFavouriteAnimeListUseCase
import com.miraeldev.favourites.domain.useCases.LoadAnimeListUseCase
import com.miraeldev.favourites.domain.useCases.SaveSearchTextUseCase
import com.miraeldev.favourites.domain.useCases.SearchAnimeByNameUseCase
import com.miraeldev.favourites.domain.useCases.SearchAnimeItemInDatabaseUseCase
import com.miraeldev.favourites.domain.useCases.SelectAnimeItemUseCase
import com.miraeldev.result.ResultAnimeInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
    val searchTextState: State<String> = _searchTextState

    private val loadingFlow = MutableSharedFlow<FavouriteListScreenState.Loading>()

    val screenState = getFavouriteAnimeListUseCase()
        .onStart { FavouriteListScreenState.Loading as FavouriteListScreenState }
        .map {
            when (val res = it) {
                is ResultAnimeInfo.Failure -> {
                    FavouriteListScreenState.Failure(failure = res.failureCause) as FavouriteListScreenState
                }

                is ResultAnimeInfo.Success -> {
                    FavouriteListScreenState.Result(result = res.animeList.toImmutableList()) as FavouriteListScreenState
                }

                is ResultAnimeInfo.Initial -> {
                    FavouriteListScreenState.Loading as FavouriteListScreenState
                }
            }
        }
        .mergeWith(loadingFlow)
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