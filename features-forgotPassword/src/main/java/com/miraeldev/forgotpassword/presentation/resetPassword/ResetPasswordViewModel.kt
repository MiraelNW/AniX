package com.miraeldev.forgotpassword.presentation.resetPassword

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.forgotpassword.domain.usecases.SaveNewPasswordUseCase
import com.miraeldev.utils.PasswordValidationState
import com.miraeldev.utils.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val validatePassword: ValidatePassword,
    private val saveNewPasswordUseCase: SaveNewPasswordUseCase
) : ViewModel() {

    private var _newPassword = mutableStateOf("")
    val newPassword: State<String> = _newPassword

    private var _repeatedPassword = mutableStateOf("")
    val repeatedPassword: State<String> = _repeatedPassword

    private val _isPasswordError = MutableStateFlow(PasswordValidationState())
    val isPasswordError = _isPasswordError.asStateFlow()

    private val _isPasswordNotEqualsError = MutableStateFlow(true)
    val isPasswordNotEqualsError = _isPasswordNotEqualsError.asStateFlow()

    private val _isPasswordSaveComplete = MutableStateFlow(true)
    val isPasswordSaveComplete = _isPasswordSaveComplete.asStateFlow()

    fun updateNewPasswordText(value: String) {
        _newPassword.value = value
    }

    fun updateRepeatedPasswordText(value: String) {
        _repeatedPassword.value = value
    }

    fun saveNewPassword(email:String) {
        viewModelScope.launch {
            saveNewPasswordUseCase(email = email, password = repeatedPassword.value)
        }
    }

    fun refreshPasswordError() {
        _isPasswordNotEqualsError.value = true
        _isPasswordError.value = PasswordValidationState(
            hasMinimum = true,
            hasCapitalizedLetter = true,
            successful = true
        )
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
}