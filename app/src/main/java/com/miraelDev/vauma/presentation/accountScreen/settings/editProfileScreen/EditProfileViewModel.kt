package com.miraelDev.vauma.presentation.accountScreen.settings.editProfileScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.domain.models.user.PasswordValidationState
import com.miraelDev.vauma.domain.usecases.userUseCase.GetUserUseCase
import com.miraelDev.vauma.presentation.auth.utils.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val validatePassword: ValidatePassword,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

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

    init {
        viewModelScope.launch {
            updateEmail(getUserUseCase().email)
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

        }
    }

    fun resetAllChanges() {
        updateNewPassword("")
        updateCurrentPassword("")
        updateRepeatedPassword("")
        refreshPasswordError()
    }
}