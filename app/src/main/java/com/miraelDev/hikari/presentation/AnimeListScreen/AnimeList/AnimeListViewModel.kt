package com.miraelDev.hikari.presentation.AnimeListScreen.AnimeList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miraelDev.hikari.domain.usecases.homeUseCase.GetFilmsAnimeListUseCase
import com.miraelDev.hikari.domain.usecases.homeUseCase.GetNameAnimeListUseCase
import com.miraelDev.hikari.domain.usecases.homeUseCase.GetNewAnimeListUseCase
import com.miraelDev.hikari.domain.usecases.homeUseCase.GetPopularAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(

    private val getNewAnimeListUseCase: GetNewAnimeListUseCase,
    private val getFilmsAnimeListUseCase: GetFilmsAnimeListUseCase,
    private val getNameAnimeListUseCase: GetNameAnimeListUseCase,
    private val getPopularAnimeListUseCase: GetPopularAnimeListUseCase

) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("tag", "crash")
    }

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