package com.miraeldev.animelist.presentation.categories.categoriesComponent

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.animelist.domain.useCases.paging.GetFilmsPagingAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.paging.GetNamePagingAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.paging.GetNewPagingAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.paging.GetPopularPagingAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.paging.LoadFilmCategoryNextPageUseCase
import com.miraeldev.animelist.domain.useCases.paging.LoadNameCategoryNextPageUseCase
import com.miraeldev.animelist.domain.useCases.paging.LoadNewCategoryNextPageUseCase
import com.miraeldev.animelist.domain.useCases.paging.LoadPopularCategoryNextPageUseCase
import com.miraeldev.animelist.presentation.categories.categoriesComponent.CategoriesStore.Intent
import com.miraeldev.animelist.presentation.categories.categoriesComponent.CategoriesStore.Label
import com.miraeldev.animelist.presentation.categories.categoriesComponent.CategoriesStore.State
import com.miraeldev.models.paging.LoadState
import com.miraeldev.models.paging.PagingState
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

interface CategoriesStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class OnAnimeItemClick(val id: Int) : Intent
        data object LoadNewCategoryNextPage : Intent
        data object LoadPopularCategoryNextPage : Intent
        data object LoadNameCategoryNextPage : Intent
        data object LoadFilmCategoryNextPage : Intent
    }

    data class State(
        val newListState: PagingState = PagingState(emptyList(), LoadState.EMPTY),
        val popularListState: PagingState = PagingState(emptyList(), LoadState.EMPTY),
        val nameListState: PagingState = PagingState(emptyList(), LoadState.EMPTY),
        val filmsListState: PagingState = PagingState(emptyList(), LoadState.EMPTY)
    )

    sealed interface Label {
        data class OnAnimeItemClick(val id: Int) : Label
    }
}

@Inject
class CategoriesStoreFactory(
    private val storeFactory: StoreFactory,
    private val getNewPagingAnimeListUseCase: GetNewPagingAnimeListUseCase,
    private val getFilmsPagingAnimeListUseCase: GetFilmsPagingAnimeListUseCase,
    private val getNamePagingAnimeListUseCase: GetNamePagingAnimeListUseCase,
    private val getPopularPagingAnimeListUseCase: GetPopularPagingAnimeListUseCase,

    private val loadNewCategoryNextPageUseCase: LoadNewCategoryNextPageUseCase,
    private val loadPopularCategoryNextPageUseCase: LoadPopularCategoryNextPageUseCase,
    private val loadNameCategoryNextPageUseCase: LoadNameCategoryNextPageUseCase,
    private val loadFilmCategoryNextPageUseCase: LoadFilmCategoryNextPageUseCase
) {

    fun create(): CategoriesStore =
        object :
            CategoriesStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "CategoriesStore",
                initialState = State(),
                bootstrapper = BootstrapperImpl(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class LoadNewPagingList(val list: PagingState) : Action
        data class LoadPopularPagingList(val list: PagingState) : Action
        data class LoadNamePagingList(val list: PagingState) : Action
        data class LoadFilmsPagingList(val list: PagingState) : Action
    }

    private sealed interface Msg {
        data class LoadNewPagingList(val list: PagingState) : Msg
        data class LoadPopularPagingList(val list: PagingState) : Msg
        data class LoadNamePagingList(val list: PagingState) : Msg
        data class LoadFilmsPagingList(val list: PagingState) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                loadNewCategoryNextPageUseCase()
                getNewPagingAnimeListUseCase().collect {
                    dispatch(Action.LoadNewPagingList(it))
                }
            }
            scope.launch {
                loadPopularCategoryNextPageUseCase()
                getPopularPagingAnimeListUseCase().collect {
                    dispatch(Action.LoadPopularPagingList(it))
                }
            }
            scope.launch {
                loadNameCategoryNextPageUseCase()
                getNamePagingAnimeListUseCase().collect {
                    dispatch(Action.LoadNamePagingList(it))
                }
            }
            scope.launch {
                loadFilmCategoryNextPageUseCase()
                getFilmsPagingAnimeListUseCase().collect {
                    dispatch(Action.LoadFilmsPagingList(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.OnAnimeItemClick -> publish(Label.OnAnimeItemClick(intent.id))
                Intent.LoadFilmCategoryNextPage -> scope.launch {
                    loadFilmCategoryNextPageUseCase()
                }
                Intent.LoadNameCategoryNextPage -> scope.launch {
                    loadNameCategoryNextPageUseCase()
                }
                Intent.LoadNewCategoryNextPage -> scope.launch {
                    loadNewCategoryNextPageUseCase()
                }
                Intent.LoadPopularCategoryNextPage -> scope.launch {
                    loadPopularCategoryNextPageUseCase()
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.LoadNewPagingList -> {
                    dispatch(Msg.LoadNewPagingList(action.list))
                }

                is Action.LoadPopularPagingList -> {
                    dispatch(Msg.LoadPopularPagingList(action.list))
                }

                is Action.LoadNamePagingList -> {
                    dispatch(Msg.LoadNamePagingList(action.list))
                }

                is Action.LoadFilmsPagingList -> {
                    dispatch(Msg.LoadFilmsPagingList(action.list))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.LoadNewPagingList -> {
                copy(newListState = msg.list)
            }

            is Msg.LoadPopularPagingList -> {
                copy(popularListState = msg.list)
            }

            is Msg.LoadNamePagingList -> {
                copy(nameListState = msg.list)
            }

            is Msg.LoadFilmsPagingList -> {
                copy(filmsListState = msg.list)
            }
        }
    }
}
