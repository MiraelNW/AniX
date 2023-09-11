package com.miraelDev.vauma.presentation.animeListScreen.animeList

import android.util.Log
import androidx.lifecycle.ViewModel
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

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("tag", "crash")
    }

    val newAnimeList = getNewAnimeListUseCase()
        .filterNotNull()

    val popularAnimeList = getPopularAnimeListUseCase()
        .filterNotNull()

    val nameAnimeList = getNameAnimeListUseCase()
        .filterNotNull()

    val filmsAnimeList = getFilmsAnimeListUseCase()
        .filterNotNull()
}