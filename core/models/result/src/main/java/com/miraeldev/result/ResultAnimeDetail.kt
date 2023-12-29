package com.miraeldev.result

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.anime.AnimeInfo

sealed class ResultAnimeDetail {

    data class  Success(val animeList: List<AnimeDetailInfo>) : ResultAnimeDetail()

    data class Failure(val failureCause: FailureCauses) : ResultAnimeDetail()

    data object Initial : ResultAnimeDetail()

}