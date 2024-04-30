package com.miraeldev.models.result

import com.miraeldev.anime.AnimeDetailInfo

sealed class ResultAnimeDetail {

    data class Success(val animeList: List<AnimeDetailInfo>) : ResultAnimeDetail()

    data class Failure(val failureCause: FailureCauses) : ResultAnimeDetail()

    data object Initial : ResultAnimeDetail()
}