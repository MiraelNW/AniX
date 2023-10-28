package com.miraeldev.animelist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miraeldev.animelist.domain.useCases.GetFilmsAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.GetNameAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.GetNewAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.GetPopularAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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