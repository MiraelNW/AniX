package com.miraelDev.hikari.presentation.FavouriteListScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.result.Result
import com.miraelDev.hikari.domain.usecases.SearchUsecase.SaveSearchTextUseCase
import com.miraelDev.hikari.domain.usecases.SearchUsecase.SearchAnimeByNameUseCase
import com.miraelDev.hikari.domain.usecases.favouriteListUseCase.GetFavouriteAnimeListUseCase
import com.miraelDev.hikari.domain.usecases.favouriteListUseCase.LoadAnimeListUseCase
import com.miraelDev.hikari.domain.usecases.favouriteListUseCase.SearchAnimeItemInDatabaseUseCase
import com.miraelDev.hikari.domain.usecases.favouriteListUseCase.SelectAnimeItemUseCase
import com.miraelDev.hikari.exntensions.mergeWith
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val loadingFlow = MutableSharedFlow<FavouriteListScreenState.Loading>()

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState

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

    fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo) {
        viewModelScope.launch {
            selectAnimeItemUseCase(isSelected, animeInfo)
        }
    }

    fun searchAnimeItemInDatabase(name: String) {
        viewModelScope.launch {
            loadingFlow.emit(FavouriteListScreenState.Loading)
            searchAnimeItemInDatabaseUseCase(name)
        }
    }

    fun loadAnimeList() {
        viewModelScope.launch {
            loadAnimeListUseCase()
        }
    }

    fun searchAnimeByName(name:String) {
        viewModelScope.launch {
            searchAnimeByNameUseCase(name)
            saveSearchTextUseCase(name)
        }
    }
}