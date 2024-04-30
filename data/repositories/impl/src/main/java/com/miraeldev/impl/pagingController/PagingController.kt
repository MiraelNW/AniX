@file:Suppress("MaxLineLength")
package com.miraeldev.impl.pagingController

import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.paging.LastDbNode
import com.miraeldev.models.paging.LoadState
import com.miraeldev.models.paging.PagingAnimeInfo
import com.miraeldev.models.paging.PagingState
import com.miraeldev.models.paging.toAnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.tatarka.inject.annotations.Inject
import java.util.concurrent.TimeUnit

@Inject
internal class PagingController(
    val pagingRequest: suspend (Long) -> List<PagingAnimeInfo>,
    val lastNodeInDb: suspend () -> LastDbNode,
    val getAnimeListByPage: suspend (Long) -> List<PagingAnimeInfo>,
    val cashSuccessNetworkResult: suspend (List<PagingAnimeInfo>, Long, Boolean) -> Unit,
    val currentTime: Long
) {

    private var page = INITIAL_PAGE
    private var endOfPaginationReached = false

    private val resultList = mutableListOf<AnimeInfo>()

    private val _pagingState = MutableStateFlow(PagingState(emptyList(), LoadState.EMPTY))
    private val pagingState = _pagingState.asStateFlow()

    val flow: Flow<PagingState> = pagingState

    suspend fun loadNextPage() {
        if (page == INITIAL_PAGE || !endOfPaginationReached && _pagingState.value.loadState == LoadState.REQUEST_INACTIVE) {
            _pagingState.update {
                if (page == INITIAL_PAGE) _pagingState.value.copy(loadState = LoadState.REFRESH_LOADING)
                else _pagingState.value.copy(loadState = LoadState.APPEND_LOADING)
            }
        }

        try {
            val cacheTimeout = TimeUnit.MILLISECONDS.convert(TWELVE_HOURS, TimeUnit.HOURS)
            val lastDbNode = lastNodeInDb()

            val data =
                if (currentTime - lastDbNode.createAt >= cacheTimeout || page > lastDbNode.page) {
                    val networkResults = pagingRequest(page)
                    cashSuccessNetworkResult(
                        networkResults,
                        page,
                        networkResults.last().isLast
                    )
                    Pair(
                        networkResults.map { it.toAnimeInfo() },
                        networkResults.last().isLast
                    )
                } else {
                    val response = getAnimeListByPage(page)
                    val lastItem = response.last()
                    Pair(response.map { it.toAnimeInfo() }, lastItem.isLast)
                }

            val list = data.first
            val isLast = data.second

            endOfPaginationReached = isLast

            if (page == INITIAL_PAGE) {
                if (list.isEmpty()) {
                    _pagingState.update { _pagingState.value.copy(loadState = LoadState.EMPTY) }
                    return
                }
                resultList.clear()
                resultList.addAll(list)
            } else {
                resultList.addAll(list)
            }

            _pagingState.update {
                _pagingState.value.copy(
                    list = resultList.toList(),
                    loadState = LoadState.REQUEST_INACTIVE
                )
            }

            if (!endOfPaginationReached) {
                page++
            }

            if (endOfPaginationReached) {
                _pagingState.update { _pagingState.value.copy(loadState = LoadState.REACH_END) }
            }
        } catch (e: Exception) {
            _pagingState.update {
                if (page == INITIAL_PAGE) _pagingState.value.copy(loadState = LoadState.REFRESH_ERROR)
                else _pagingState.value.copy(loadState = LoadState.APPEND_ERROR)
            }
        }
    }

    companion object {
        private const val INITIAL_PAGE = 0L
        private const val TWELVE_HOURS = 12L
    }
}