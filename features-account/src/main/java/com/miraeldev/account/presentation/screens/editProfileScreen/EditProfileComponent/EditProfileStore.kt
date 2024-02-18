package com.miraeldev.account.presentation.screens.editProfileScreen.EditProfileComponent

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.account.domain.ChangePasswordUseCase
import com.miraeldev.account.domain.GetUserEmailUseCase
import com.miraeldev.account.domain.GetUserInfoUseCase
import com.miraeldev.account.domain.UserModel
import com.miraeldev.account.domain.model.EditProfileErrorModel
import com.miraeldev.account.domain.toUserModel
import com.miraeldev.account.presentation.screens.editProfileScreen.EditProfileComponent.EditProfileStore.Intent
import com.miraeldev.account.presentation.screens.editProfileScreen.EditProfileComponent.EditProfileStore.Label
import com.miraeldev.account.presentation.screens.editProfileScreen.EditProfileComponent.EditProfileStore.State
import com.miraeldev.utils.PasswordValidationState
import com.miraeldev.utils.ValidatePassword
import kotlinx.coroutines.launch
import javax.inject.Inject

interface EditProfileStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class OnChangeImage(val image: String) : Intent
        data class OnChangeUsername(val username: String) : Intent
        data class OnChangeEmail(val email: String) : Intent
        data class OnChangeCurrentPassword(val currentPassword: String) : Intent
        data class OnChangePassword(val password: String) : Intent
        data class OnChangeRepeatedPassword(val repeatedPassword: String) : Intent
        data class OnChangePasswordClick(
            val currentPassword: String,
            val password: String,
            val repeatedPassword: String
        ) : Intent

        data class UpdateUserInfo(val image: String, val email: String, val username: String) :
            Intent

        data object OnBackClick : Intent
        data object ResetAllChanges : Intent
        data object RefreshPasswordError : Intent
        data object RefreshRepeatedPasswordError : Intent
        data object RefreshEmailError : Intent
    }

    data class State(
        val userModel: UserModel,
        val image: String,
        val username: String,
        val email: String,
        val currentPassword: String,
        val password: String,
        val repeatedPassword: String,
        val editProfileErrorModel: EditProfileErrorModel
    )

    sealed interface Label {
        data object OnBackClick : Label
    }
}

class EditProfileStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val validatePassword: ValidatePassword,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
//    private val updateUserInfo
) {

    fun create(): EditProfileStore =
        object : EditProfileStore, Store<Intent, State, Label> by storeFactory.create(
            name = "EditProfileStore",
            initialState = State(
                UserModel(),
                "",
                "",
                "",
                "",
                "",
                "",
                EditProfileErrorModel()
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class UserLoaded(val userModel: UserModel) : Action
    }

    private sealed interface Msg {
        data class UserLoaded(val user: UserModel) : Msg
        data class OnChangeImage(val image: String) : Msg
        data class OnChangeUsername(val username: String) : Msg
        data class OnChangeEmail(val email: String) : Msg
        data class OnChangeCurrentPassword(val currentPassword: String) : Msg
        data class OnChangePassword(val password: String) : Msg
        data class OnChangeRepeatedPassword(val repeatedPassword: String) : Msg
        data class EditProfileError(val error: EditProfileErrorModel) : Msg
        data object ResetAllChanges : Msg
        data object RefreshPasswordError : Msg
        data object RefreshRepeatedPasswordError : Msg
        data object RefreshEmailError : Msg

    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                val user = getUserInfoUseCase().toUserModel()
                dispatch(Action.UserLoaded(user))
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.OnChangeImage -> {
                    dispatch(Msg.OnChangeImage(intent.image))
                }

                is Intent.OnChangeUsername -> {
                    dispatch(Msg.OnChangeUsername(intent.username))
                }

                is Intent.OnChangeEmail -> {
                    dispatch(Msg.OnChangeEmail(intent.email))
                }

                is Intent.OnChangeCurrentPassword -> {
                    dispatch(Msg.OnChangeCurrentPassword(intent.currentPassword))

                }

                is Intent.OnChangePassword -> {
                    dispatch(Msg.OnChangePassword(intent.password))

                }

                is Intent.OnChangeRepeatedPassword -> {
                    dispatch(Msg.OnChangeRepeatedPassword(intent.repeatedPassword))

                }

                is Intent.OnChangePasswordClick -> {
                    val currentPasswordState =
                        validatePassword.execute(intent.currentPassword.trim())
                    val passwordState = validatePassword.execute(intent.password.trim())
                    val passwordsNotEquals =
                        intent.password.trim() != intent.repeatedPassword.trim()
                    if (currentPasswordState.successful && passwordState.successful && passwordsNotEquals) {
                        scope.launch {
                            val success = changePasswordUseCase(
                                intent.currentPassword,
                                intent.password,
                                intent.repeatedPassword
                            )
                            if (success) {
//                                dispatch()
                            } else {
                                dispatch(Msg.EditProfileError(EditProfileErrorModel(passwordNetworkError = true)))
                            }
                        }
                    } else {
                        dispatch(
                            Msg.EditProfileError(
                                EditProfileErrorModel(
                                    passwordError = passwordState,
                                    repeatedPasswordError = passwordsNotEquals
                                )
                            )
                        )
                    }
                }

                is Intent.UpdateUserInfo -> {
                    scope.launch {
//                        val success = updateUserInfoUseCase()
                    }
                }

                is Intent.OnBackClick -> {
                    publish(Label.OnBackClick)

                }

                is Intent.ResetAllChanges -> {
                    dispatch(Msg.ResetAllChanges)

                }

                is Intent.RefreshPasswordError -> {
                    dispatch(Msg.RefreshPasswordError)

                }

                is Intent.RefreshRepeatedPasswordError -> {
                    dispatch(Msg.RefreshRepeatedPasswordError)

                }

                is Intent.RefreshEmailError -> {
                    dispatch(Msg.RefreshEmailError)

                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.UserLoaded -> {
                    dispatch(Msg.UserLoaded(action.userModel))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.UserLoaded -> {
                    copy(userModel = msg.user)
                }

                is Msg.OnChangeImage -> {
                    copy(image = msg.image)
                }

                is Msg.OnChangeUsername -> {
                    copy(username = msg.username)
                }

                is Msg.OnChangeEmail -> {
                    copy(email = msg.email)
                }

                is Msg.OnChangeCurrentPassword -> {
                    copy(currentPassword = msg.currentPassword)
                }

                is Msg.OnChangePassword -> {
                    copy(password = msg.password)
                }

                is Msg.OnChangeRepeatedPassword -> {
                    copy(repeatedPassword = msg.repeatedPassword)
                }

                is Msg.ResetAllChanges -> {
                    copy(
                        currentPassword = "",
                        password = "",
                        repeatedPassword = "",
                        editProfileErrorModel = EditProfileErrorModel()
                    )
                }

                is Msg.EditProfileError -> {
                    copy(editProfileErrorModel = msg.error)
                }

                is Msg.RefreshPasswordError -> {
                    copy(editProfileErrorModel = editProfileErrorModel.copy(passwordError = PasswordValidationState()))
                }

                is Msg.RefreshRepeatedPasswordError -> {
                    copy(editProfileErrorModel = editProfileErrorModel.copy(repeatedPasswordError = false))
                }

                is Msg.RefreshEmailError -> {
                    copy(editProfileErrorModel = editProfileErrorModel.copy(emailError = false))
                }
            }
    }
}
