package com.miraeldev.impl.pagingController

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.models.paging.LoadState
import com.miraeldev.models.paging.PagingAnimeInfo
import com.miraeldev.models.paging.PagingState
import com.miraeldev.models.paging.toAnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.tatarka.inject.annotations.Inject

@Inject
internal class SearchResultsPagingController(
    private val name: String,
    private val yearFilter: String?,
    private val sortFilter: String?,
    private val genreListFilter: List<String>,
    val pagingRequest: suspend (String, String?, String?, String, Int, Int) -> List<PagingAnimeInfo>,
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
            val yearCode = getYearCode(yearFilter)
            val genreCode = getGenreCodeFromList(genreListFilter)
            val sortCode = getSortCode(sortFilter)

            val data = pagingRequest(name, yearCode, sortCode, genreCode, page, PAGE_SIZE)

            val list = data.map { it.toAnimeInfo() }
            val isLast = data.lastOrNull()?.isLast ?: false

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



    private fun getGenreCodeFromList(genreList: List<String>): String {
        var codeResult = ""
        genreList.forEach { genre ->
            val litterCode = when (genre) {

                "Сенен" -> 'a'

                "Седзе" -> 'b'

                "Комедия" -> 'c'

                "Романтика" -> 'd'

                "Школа" -> 'e'

                "Боевые искусства" -> 'f'

                "Гарем" -> 'g'

                "Детектив" -> 'h'

                "Драма" -> 'i'

                "Повседневность" -> 'j'

                "Приключение" -> 'k'

                "Психологическое" -> 'l'

                "Сверхъестественное" -> 'm'

                "Спорт" -> 'n'

                "Ужасы" -> 'o'

                "Фантастика" -> 'p'

                "Фэнтези" -> 'q'

                "Экшен" -> 'r'

                "Триллер" -> 's'

                "Супер сила" -> 't'

                "Гурман" -> 'u'

                else -> ""
            }

            codeResult += litterCode
        }
        return codeResult
    }

    private fun getYearCode(year: String?): String? {
        if (year == null) return null
        return when (year) {
            "Онгоинг" -> "h"
            "2023" -> "g"
            "2022" -> "f"
            "2021" -> "e"
            "2015-2020" -> "d"
            "2008-2014" -> "c"
            "2000-2007" -> "b"
            "до 2000" -> "a"
            else -> null
        }
    }

    private fun getSortCode(sort: String?): String? {
        return when (sort) {
            "Алфавиту" -> "name"
            "Рейтингу" -> "popular"
            "Количеству серий" -> "c"
            "Году выхода" -> "new"
            else -> null
        }
    }

    companion object {
        private const val INITIAL_PAGE = 0
        private const val PAGE_SIZE = 20
    }

}