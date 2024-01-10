package com.miraeldev.account.presentation.settings.editProfileScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.account.domain.ChangePasswordUseCase
import com.miraeldev.account.domain.GetUserEmailUseCase
import com.miraeldev.account.domain.GetUserInfoUseCase
import com.miraeldev.account.domain.UserModel
import com.miraeldev.utils.PasswordValidationState
import com.miraeldev.utils.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val validatePassword: ValidatePassword,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
) : ViewModel() {

    val userInfo = getUserInfoUseCase()
        .map {
            UserModel(
                id = it.id,
                username = it.username,
                name = it.name,
                image = it.image,
                email = it.email
            )
        }
        .onEach {
            _emailState.value = it.email
            _nickNameState.value = it.name
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, UserModel.Empty as UserModel)

    private val _nickNameState = mutableStateOf("")
    val nickNameState: State<String> = _nickNameState

    private val _emailState = mutableStateOf("")
    val emailState: State<String> = _emailState

    private val _currentPassword = mutableStateOf("")
    val currentPassword: State<String> = _currentPassword

    private val _newPassword = mutableStateOf("")
    val newPassword: State<String> = _newPassword

    private val _repeatedPassword = mutableStateOf("")
    val repeatedPassword: State<String> = _repeatedPassword

    private val _isPasswordError = MutableStateFlow(PasswordValidationState())
    val isPasswordError = _isPasswordError.asStateFlow()

    private val _isPasswordNotEqualsError = MutableStateFlow(true)
    val isPasswordNotEqualsError = _isPasswordNotEqualsError.asStateFlow()

    private val _isEmailError = MutableStateFlow(false)
    val isEmailError: StateFlow<Boolean> = _isEmailError.asStateFlow()

    private val _serverError = MutableStateFlow(false)
    val serverError: StateFlow<Boolean> = _serverError.asStateFlow()

    init {
        viewModelScope.launch {
            updateEmail(getUserEmailUseCase())
        }
    }

    fun updateNickName(nickName: String) {
        _nickNameState.value = nickName
    }

    fun updateEmail(email: String) {
        _emailState.value = email
    }

    fun updateCurrentPassword(currPassword: String) {
        _currentPassword.value = currPassword
    }

    fun updateNewPassword(newPassword: String) {
        _newPassword.value = newPassword
    }

    fun updateRepeatedPassword(repeatedPassword: String) {
        _repeatedPassword.value = repeatedPassword
    }

    fun refreshEmailError() {
        _isEmailError.value = false
    }

    fun refreshPasswordError() {
        viewModelScope.launch {
//            isPasswordErrorFlow.emit(false)
        }
        _serverError.value = false
        _isPasswordNotEqualsError.value = true
        _isPasswordError.value = PasswordValidationState(
            hasMinimum = true,
            hasCapitalizedLetter = true,
            successful = true
        )
    }

    fun isCurrentPasswordValid(): Boolean {
        val passwordState = validatePassword.execute(currentPassword.value)
        _isPasswordError.value = passwordState
        return passwordState.successful
    }

    fun isNewPasswordValid(): Boolean {
        val passwordState = validatePassword.execute(newPassword.value)
        _isPasswordError.value = passwordState
        return passwordState.successful
    }

    fun isPasswordEquals(): Boolean {
        _isPasswordNotEqualsError.value =
            newPassword.value == repeatedPassword.value && newPassword.value.isNotEmpty()
        return newPassword.value == repeatedPassword.value && newPassword.value.isNotEmpty()
    }

    fun changePassword() {
        viewModelScope.launch {
            _serverError.value = changePasswordUseCase(
                currentPassword = currentPassword.value,
                newPassword = newPassword.value,
                repeatedPassword = repeatedPassword.value
            )
        }
    }

    fun resetAllChanges() {
        updateNewPassword("")
        updateCurrentPassword("")
        updateRepeatedPassword("")
        refreshPasswordError()
    }
}