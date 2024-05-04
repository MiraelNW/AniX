package com.miraeldev.account.presentation.screens.account.accountComponent

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.account.data.Logger
import com.miraeldev.account.domain.GetUserInfoUseCase
import com.miraeldev.account.domain.LogOutUseCase
import com.miraeldev.account.domain.SetDarkThemeUseCase
import com.miraeldev.account.domain.UserModel
import com.miraeldev.account.domain.toUserModel
import com.miraeldev.account.presentation.screens.account.accountComponent.AccountStore.Intent
import com.miraeldev.account.presentation.screens.account.accountComponent.AccountStore.Label
import com.miraeldev.account.presentation.screens.account.accountComponent.AccountStore.State
import com.miraeldev.models.anime.Settings
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

interface AccountStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object LogOut : Intent
        data class OnSettingItemClick(val settings: Settings) : Intent
        data class OnDarkThemeClick(val isDarkTheme: Boolean) : Intent
    }

    data class State(val userModel: UserModel, val logOutError: Boolean)

    sealed interface Label {
        data class OnSettingItemClick(val settings: Settings) : Label
        data object OnLogOutComplete : Label
    }
}

@Inject
class AccountStoreFactory(
    private val storeFactory: StoreFactory,
    private val logOutUseCase: LogOutUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
    private val logger: Logger
) {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        logger.logError(throwable.message.toString(), throwable)
    }

    fun create(): AccountStore =
        object :
            AccountStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "AccountStore",
                initialState = State(UserModel(), false),
                bootstrapper = BootstrapperImpl(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class UserLoaded(val user: UserModel) : Action
    }

    private sealed interface Msg {
        data object LogOutError : Msg
        data class UserLoaded(val user: UserModel) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch(exceptionHandler) {
                val user = getUserInfoUseCase().toUserModel()
                dispatch(Action.UserLoaded(user))
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.LogOut -> {
                    scope.launch {
                        val isSuccess = logOutUseCase()
                        if (isSuccess) publish(Label.OnLogOutComplete) else dispatch(Msg.LogOutError)
                    }
                }

                is Intent.OnSettingItemClick -> {
                    publish(Label.OnSettingItemClick(intent.settings))
                }

                is Intent.OnDarkThemeClick -> scope.launch { setDarkThemeUseCase(intent.isDarkTheme) }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.UserLoaded -> {
                    dispatch(Msg.UserLoaded(action.user))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.LogOutError -> {
                    copy(logOutError = true)
                }

                is Msg.UserLoaded -> {
                    copy(userModel = msg.user)
                }
            }
    }
}
