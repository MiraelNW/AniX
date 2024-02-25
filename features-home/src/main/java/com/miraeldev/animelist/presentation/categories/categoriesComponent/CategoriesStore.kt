package com.miraeldev.animelist.presentation.categories.categoriesComponent

import androidx.paging.PagingData
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.animelist.domain.useCases.paging.GetFilmsPagingAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.paging.GetNamePagingAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.paging.GetNewPagingAnimeListUseCase
import com.miraeldev.animelist.domain.useCases.paging.GetPopularPagingAnimeListUseCase
import com.miraeldev.animelist.presentation.categories.categoriesComponent.CategoriesStore.Intent
import com.miraeldev.animelist.presentation.categories.categoriesComponent.CategoriesStore.Label
import com.miraeldev.animelist.presentation.categories.categoriesComponent.CategoriesStore.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import me.tatarka.inject.annotations.Inject

interface CategoriesStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class OnAnimeItemClick(val id: Int) : Intent
    }

    data class State(
        val newListState: Flow<PagingData<AnimeInfo>> = emptyFlow(),
        val popularListState: Flow<PagingData<AnimeInfo>> = emptyFlow(),
        val nameListState: Flow<PagingData<AnimeInfo>> = emptyFlow(),
        val filmsListState: Flow<PagingData<AnimeInfo>> = emptyFlow()
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
    private val getPopularPagingAnimeListUseCase: GetPopularPagingAnimeListUseCase
) {

    fun create(): CategoriesStore =
        object : CategoriesStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CategoriesStore",
            initialState = State(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class LoadNewPagingList(val list: Flow<PagingData<AnimeInfo>>) : Action
        data class LoadPopularPagingList(val list: Flow<PagingData<AnimeInfo>>) : Action
        data class LoadNamePagingList(val list: Flow<PagingData<AnimeInfo>>) : Action
        data class LoadFilmsPagingList(val list: Flow<PagingData<AnimeInfo>>) : Action
    }

    private sealed interface Msg {
        data class LoadNewPagingList(val list: Flow<PagingData<AnimeInfo>>) : Msg
        data class LoadPopularPagingList(val list: Flow<PagingData<AnimeInfo>>) : Msg
        data class LoadNamePagingList(val list: Flow<PagingData<AnimeInfo>>) : Msg
        data class LoadFilmsPagingList(val list: Flow<PagingData<AnimeInfo>>) : Msg

    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            dispatch(Action.LoadNewPagingList(getNewPagingAnimeListUseCase()))
            dispatch(Action.LoadPopularPagingList(getPopularPagingAnimeListUseCase()))
            dispatch(Action.LoadNamePagingList(getNamePagingAnimeListUseCase()))
            dispatch(Action.LoadFilmsPagingList(getFilmsPagingAnimeListUseCase()))
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.OnAnimeItemClick -> {
                    publish(Label.OnAnimeItemClick(intent.id))
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
