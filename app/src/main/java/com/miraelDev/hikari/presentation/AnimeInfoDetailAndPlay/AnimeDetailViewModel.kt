package com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay

import androidx.lifecycle.ViewModel
import com.miraelDev.hikari.domain.usecases.GetAnimeDetailUseCase
import com.miraelDev.hikari.domain.usecases.LoadAnimeDetailUseCase
import com.miraelDev.hikari.domain.usecases.LoadVideoIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    val loadVideoIdUseCase: LoadVideoIdUseCase,
    val loadAnimeDetailUseCase: LoadAnimeDetailUseCase,
) : ViewModel() {

    val animeDetail = getAnimeDetailUseCase()

}