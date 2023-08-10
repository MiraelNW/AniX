package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.hikari.domain.result.Result
import com.miraelDev.hikari.domain.usecases.GetAnimeDetailUseCase
import com.miraelDev.hikari.domain.usecases.LoadAnimeDetailUseCase
import com.miraelDev.hikari.domain.usecases.LoadVideoIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
        private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
        val loadVideoIdUseCase: LoadVideoIdUseCase,
        val loadAnimeDetailUseCase: LoadAnimeDetailUseCase,
) : ViewModel() {

    val animeDetail = getAnimeDetailUseCase()
            .map {
                when (val res = it) {

                    is Result.Success -> {
                        AnimeDetailScreenState.SearchResult(result = res.animeList) as AnimeDetailScreenState
                    }

                    is Result.Failure -> {
                        AnimeDetailScreenState.SearchFailure(failure = res.failureCause) as AnimeDetailScreenState
                    }

                    is Result.Initial -> {
                        AnimeDetailScreenState.Loading as AnimeDetailScreenState
                    }

                }
            }
            .onStart { emit(AnimeDetailScreenState.Loading) }
            .shareIn(
                    viewModelScope,
                    SharingStarted.Lazily
            )

}