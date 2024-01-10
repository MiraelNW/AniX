package com.miraeldev.animelist.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miraeldev.animelist.domain.useCases.paging.GetFilmsPagingAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.paging.GetNamePagingAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.paging.GetNewPagingAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.paging.GetPopularPagingAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(

    private val getNewPagingAnimeListUseCase: GetNewPagingAnimeListUseCase,
    private val getFilmsPagingAnimeListUseCase: GetFilmsPagingAnimeListUseCase,
    private val getNamePagingAnimeListUseCase: GetNamePagingAnimeListUseCase,
    private val getPopularPagingAnimeListUseCase: GetPopularPagingAnimeListUseCase

) : ViewModel() {

    val newAnimeList = getNewPagingAnimeListUseCase()
        .filterNotNull()
        .cachedIn(viewModelScope)

    val popularAnimeList = getPopularPagingAnimeListUseCase()
        .filterNotNull()
        .cachedIn(viewModelScope)

    val nameAnimeList = getNamePagingAnimeListUseCase()
        .filterNotNull()
        .cachedIn(viewModelScope)

    val filmsAnimeList = getFilmsPagingAnimeListUseCase()
        .filterNotNull()
        .cachedIn(viewModelScope)

}