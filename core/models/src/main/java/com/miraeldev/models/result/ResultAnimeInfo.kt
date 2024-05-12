package com.miraeldev.models.result

import com.miraeldev.models.anime.AnimeInfo

sealed class ResultAnimeInfo {

    data class Success(val animeList: List<AnimeInfo>) : ResultAnimeInfo()

    data class Failure(val failureCause: FailureCauses) : ResultAnimeInfo()

    data object Initial : ResultAnimeInfo()
}