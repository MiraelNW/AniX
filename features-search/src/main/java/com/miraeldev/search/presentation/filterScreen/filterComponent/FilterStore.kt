package com.miraeldev.search.presentation.filterScreen.filterComponent

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.anime.CategoryModel
import com.miraeldev.search.domain.usecases.filterUseCase.AddToFilterListUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.ClearAllFiltersUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.GetGenreListUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.GetSortByCategoryUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.GetYearCategoryUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.RemoveFromFilterListUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.SelectCategoryUseCase
import com.miraeldev.search.presentation.filterScreen.filterComponent.FilterStore.Intent
import com.miraeldev.search.presentation.filterScreen.filterComponent.FilterStore.Label
import com.miraeldev.search.presentation.filterScreen.filterComponent.FilterStore.State
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

interface FilterStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class SelectCategory(
            val categoryId: Int,
            val category: String,
            val isSelected: Boolean
        ) : Intent

        data object ClearAllFilter : Intent
        data object OnBackPressed : Intent
    }

    data class State(
        val genreList: List<CategoryModel>,
        val yearCategory: String,
        val sortByCategory: String
    )

    sealed interface Label {
        data object OnBackPressed : Label
    }
}

@Inject
class FilterStoreFactory(
    private val storeFactory: StoreFactory,
    private val clearAllFilters: ClearAllFiltersUseCase,
    private val addToFilterList: AddToFilterListUseCase,
    private val removeFromFilterList: RemoveFromFilterListUseCase,
    private val selectCategory: SelectCategoryUseCase,
    private val getGenreList: GetGenreListUseCase,
    private val getSortByCategory: GetSortByCategoryUseCase,
    private val getYearCategory: GetYearCategoryUseCase,
) {

    fun create(): FilterStore =
        object : FilterStore, Store<Intent, State, Label> by storeFactory.create(
            name = "FilterStore",
            initialState = State(listOf(), "", ""),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class GenreListLoaded(val genreList: List<CategoryModel>) : Action
        data class YearCategoryLoaded(val yearCategory: String) : Action
        data class SortByCategoryLoaded(val sortByCategory: String) : Action
    }

    private sealed interface Msg {
        data class GenreListLoaded(val genreList: List<CategoryModel>) : Msg
        data class YearCategoryLoaded(val yearCategory: String) : Msg
        data class SortByCategoryLoaded(val sortByCategory: String) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getGenreList().collect {
                    dispatch(Action.GenreListLoaded(it))
                }
            }
            scope.launch {
                getSortByCategory().collect {
                    dispatch(Action.SortByCategoryLoaded(it))
                }
            }
            scope.launch {
                getYearCategory().collect {
                    dispatch(Action.YearCategoryLoaded(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.OnBackPressed -> publish(Label.OnBackPressed)
                is Intent.ClearAllFilter -> scope.launch { clearAllFilters() }
                is Intent.SelectCategory -> {
                    scope.launch {
                        selectCategory(intent.categoryId, intent.category)
                        if (intent.isSelected) {
                            removeFromFilterList(intent.categoryId, intent.category)
                        } else {
                            addToFilterList(intent.categoryId, intent.category)
                        }
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.GenreListLoaded -> dispatch(Msg.GenreListLoaded(action.genreList))
                is Action.YearCategoryLoaded -> dispatch(Msg.YearCategoryLoaded(action.yearCategory))
                is Action.SortByCategoryLoaded -> dispatch(Msg.SortByCategoryLoaded(action.sortByCategory))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.GenreListLoaded -> copy(genreList = msg.genreList)
                is Msg.YearCategoryLoaded -> copy(yearCategory = msg.yearCategory)
                is Msg.SortByCategoryLoaded -> copy(sortByCategory = msg.sortByCategory)
            }
    }
}
