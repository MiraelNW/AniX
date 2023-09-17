package com.miraelDev.vauma.presentation.animeListScreen.animeList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miraelDev.vauma.domain.usecases.homeUseCase.GetFilmsAnimeListUseCase
import com.miraelDev.vauma.domain.usecases.homeUseCase.GetNameAnimeListUseCase
import com.miraelDev.vauma.domain.usecases.homeUseCase.GetNewAnimeListUseCase
import com.miraelDev.vauma.domain.usecases.homeUseCase.GetPopularAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(

    private val getNewAnimeListUseCase: GetNewAnimeListUseCase,
    private val getFilmsAnimeListUseCase: GetFilmsAnimeListUseCase,
    private val getNameAnimeListUseCase: GetNameAnimeListUseCase,
    private val getPopularAnimeListUseCase: GetPopularAnimeListUseCase

) : ViewModel() {

    val newAnimeList = getNewAnimeListUseCase()
        .filterNotNull()
        .cachedIn(viewModelScope)

    val popularAnimeList = getPopularAnimeListUseCase()
        .filterNotNull()
        .cachedIn(viewModelScope)

    val nameAnimeList = getNameAnimeListUseCase()
        .filterNotNull()
        .cachedIn(viewModelScope)

    val filmsAnimeList = getFilmsAnimeListUseCase()
        .filterNotNull()
        .cachedIn(viewModelScope)
}