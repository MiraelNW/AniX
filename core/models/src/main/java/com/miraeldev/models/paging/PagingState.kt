package com.miraeldev.models.paging

import com.miraeldev.models.anime.AnimeInfo

data class PagingState(val list: List<AnimeInfo>, val loadState: LoadState)