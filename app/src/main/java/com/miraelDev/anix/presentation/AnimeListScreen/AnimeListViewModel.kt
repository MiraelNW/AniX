package com.miraelDev.anix.presentation.AnimeListScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.anix.domain.usecases.homeUseCase.GetAnimeListByCategoryUseCase
import com.miraelDev.anix.domain.usecases.homeUseCase.GetAnimeListUseCase
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

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