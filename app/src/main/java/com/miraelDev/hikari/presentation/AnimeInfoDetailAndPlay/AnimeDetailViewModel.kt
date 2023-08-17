package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.result.Result
import com.miraelDev.hikari.domain.usecases.animeDetailUseCase.GetAnimeDetailUseCase
import com.miraelDev.hikari.domain.usecases.animeDetailUseCase.LoadAnimeDetailUseCase
import com.miraelDev.hikari.domain.usecases.animeDetailUseCase.SelectAnimeItemUseCase
import com.miraelDev.hikari.domain.usecases.videoPlayerUseCase.LoadVideoIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
        private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
        private val selectAnimeItemUseCase: SelectAnimeItemUseCase,
        private val loadVideoIdUseCase: LoadVideoIdUseCase,
        private val loadAnimeDetailUseCase: LoadAnimeDetailUseCase,
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
            .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    AnimeDetailScreenState.Loading
            )

    fun loadVideoId(id:Int){
        viewModelScope.launch {
            loadVideoIdUseCase(id)
        }
    }

    fun loadAnimeDetail(id:Int){
        viewModelScope.launch {
            loadAnimeDetailUseCase(id)
        }
    }

    fun selectAnimeItem(isSelected: Boolean,animeInfo:AnimeInfo) {
        viewModelScope.launch {
            selectAnimeItemUseCase(isSelected, animeInfo)
        }
    }

}