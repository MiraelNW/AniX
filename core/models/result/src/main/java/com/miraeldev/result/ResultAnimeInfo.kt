package com.miraeldev.result

import com.miraeldev.anime.AnimeInfo

sealed class ResultAnimeInfo {

    data class  Success  (val  animeList: List<AnimeInfo>) : ResultAnimeInfo()

    data class Failure(val failureCause: FailureCauses) : ResultAnimeInfo()

    data object Initial : ResultAnimeInfo()

}