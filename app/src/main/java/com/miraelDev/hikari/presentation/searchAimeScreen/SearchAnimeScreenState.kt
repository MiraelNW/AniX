package com.miraelDev.hikari.presentation.searchAimeScreen

import android.os.Parcelable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.paging.PagingData
import com.miraelDev.hikari.domain.models.AnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.parcelize.Parcelize

sealed class SearchAnimeScreenState {

    object EmptyList : SearchAnimeScreenState()


    data class InitialList(
        val result: Flow<PagingData<AnimeInfo>>
    ) : SearchAnimeScreenState()

    object SearchHistory : SearchAnimeScreenState()

    data class SearchResult(
        val result: Flow<PagingData<AnimeInfo>>
    ) : SearchAnimeScreenState()

}
