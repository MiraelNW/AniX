package com.miraelDev.hikari.presentation.AnimeListScreen.AnimeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.hikari.domain.usecases.homeUseCase.GetAnimeListByCategoryUseCase
import com.miraelDev.hikari.domain.usecases.homeUseCase.GetAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val getAnimeListByCategoryUseCase: GetAnimeListByCategoryUseCase,
    private val getAnimeListUseCase: GetAnimeListUseCase,
) : ViewModel() {

    private val animeListFlow = getAnimeListUseCase()

    val screenState = animeListFlow
        .filter { it.isNotEmpty() }
        .map { AnimeListScreenState.AnimeList(animes = it) as AnimeListScreenState }
        .onStart { emit(AnimeListScreenState.Loading) }

    fun loadAnimeBtCategory(category: Int) {
        viewModelScope.launch {
            getAnimeListByCategoryUseCase(category)
        }
    }
}