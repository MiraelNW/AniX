package com.miraeldev.animelist.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.animelist.domain.useCases.AddAnimeToListUseCase
import com.miraeldev.animelist.domain.useCases.GetUserInfoUseCase
import com.miraeldev.animelist.domain.useCases.LoadVideoIdUseCase
import com.miraeldev.animelist.domain.useCases.homeScreenUseCases.GetFilmsAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.homeScreenUseCases.GetNameAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.homeScreenUseCases.GetNewAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.homeScreenUseCases.GetPopularAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.homeScreenUseCases.LoadDataUseCase
import com.miraeldev.exntensions.mergeWith
import com.miraeldev.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val loadDataUseCase: LoadDataUseCase,

    private val getNewAnimeListUseCase: GetNewAnimeListUseCase,
    private val getFilmsAnimeListUseCase: GetFilmsAnimeListUseCase,
    private val getNameAnimeListUseCase: GetNameAnimeListUseCase,
    private val getPopularAnimeListUseCase: GetPopularAnimeListUseCase,

    private val addAnimeToListUseCase: AddAnimeToListUseCase,

    private val getUserInfoUseCase: GetUserInfoUseCase,

    private val loadVideoIdUseCase: LoadVideoIdUseCase
) : ViewModel() {

    private val updateFavStatusFlow = MutableSharedFlow<User>()

    val screenState = getUserInfoUseCase()
        .mergeWith(updateFavStatusFlow)
        .map {
            HomeScreenState.Success(it) as HomeScreenState
        }
        .onStart {
            loadDataUseCase()
            emit(HomeScreenState.Loading)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, HomeScreenState.Initial)


    val newAnimeList = getNewAnimeListUseCase()
        .filterNotNull()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val popularAnimeList = getPopularAnimeListUseCase()
        .filterNotNull()
        .filter { it.isNotEmpty() }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val nameAnimeList = getNameAnimeListUseCase()
        .filterNotNull()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val filmsAnimeList = getFilmsAnimeListUseCase()
        .filterNotNull()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addAnimeToList(user: User, isSelected: Boolean, animeItem: LastWatchedAnime) {
        viewModelScope.launch {
            updateFavStatusFlow.emit(user)
            addAnimeToListUseCase(isSelected, animeItem)
        }
    }

    fun loadVideoId(animeItem: LastWatchedAnime) {
        viewModelScope.launch {
            loadVideoIdUseCase(animeItem)
        }
    }

}