package com.miraeldev.detailinfo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.detailinfo.data.repositories.Logger
import com.miraeldev.detailinfo.domain.useCases.DownloadAnimeEpisodeUseCase
import com.miraeldev.detailinfo.domain.useCases.GetAnimeDetailUseCase
import com.miraeldev.detailinfo.domain.useCases.LoadAnimeDetailUseCase
import com.miraeldev.detailinfo.domain.useCases.LoadVideoIdUseCase
import com.miraeldev.detailinfo.domain.useCases.SelectAnimeItemUseCase
import com.miraeldev.result.ResultAnimeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineExceptionHandler
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
    private val downloadAnimeEpisodeUseCase: DownloadAnimeEpisodeUseCase,
    private val logger: Logger
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        logger.logError(throwable.message ?: "", throwable)
    }

    val animeDetail = getAnimeDetailUseCase()
        .map {
            when (val res = it) {

                is ResultAnimeDetail.Success -> {
                    AnimeDetailScreenState.SearchResult(result = res.animeList.toImmutableList()) as AnimeDetailScreenState
                }

                is ResultAnimeDetail.Failure -> {
                    AnimeDetailScreenState.SearchFailure(failure = res.failureCause) as AnimeDetailScreenState
                }

                is ResultAnimeDetail.Initial -> {
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

    fun downloadEpisode(url: String, videoName: String) {
        viewModelScope.launch(exceptionHandler) {
            downloadAnimeEpisodeUseCase(url, videoName)
        }

    }

    fun loadVideoId(animeItem: AnimeDetailInfo, id: Int) {
        viewModelScope.launch(exceptionHandler) {
            loadVideoIdUseCase(animeItem, id)
        }
    }

    fun loadAnimeDetail(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            loadAnimeDetailUseCase(id)
        }
    }

    fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeDetailInfo) {
        viewModelScope.launch(exceptionHandler) {
            selectAnimeItemUseCase(isSelected, animeInfo)
        }
    }

}