package com.miraeldev.signup.presentation.signUpScreen

import android.net.Uri
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.exntensions.mergeWith
import com.miraeldev.signup.domain.useCases.GetRegistrationCompleteUseCase
import com.miraeldev.signup.domain.useCases.GetSignUpErrorUseCase
import com.miraeldev.signup.domain.useCases.SignUpUseCase
import com.miraeldev.user.User
import com.miraeldev.utils.PasswordValidationState
import com.miraeldev.utils.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUp: SignUpUseCase,
    private val signUpErrorUseCase: GetSignUpErrorUseCase,
    private val getRegistrationCompleteUseCase: GetRegistrationCompleteUseCase,
    private val validatePassword: ValidatePassword,
) : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, _ ->

    }

    private val _imagePath = mutableStateOf("")
    val imagePath: State<String> = _imagePath

    private val _nickNameTextState = mutableStateOf("")
    val nickNameTextState: State<String> = _nickNameTextState

    private val _emailTextState = mutableStateOf("")
    val emailTextState: State<String> = _emailTextState

    private val _passwordTextState = mutableStateOf("")
    val passwordTextState: State<String> = _passwordTextState

    private val _repeatedPasswordTextState = mutableStateOf("")
    val repeatedPasswordTextState: State<String> = _repeatedPasswordTextState

    private val isPasswordErrorFlow = MutableSharedFlow<Boolean>()

    private val _isPasswordError = MutableStateFlow(PasswordValidationState())
    val isPasswordError = _isPasswordError.asStateFlow()

    private val _isPasswordNotEqualsError = MutableStateFlow(true)
    val isPasswordNotEqualsError = _isPasswordNotEqualsError.asStateFlow()

    private val _isEmailError = MutableStateFlow(false)
    val isEmailError: StateFlow<Boolean> = _isEmailError.asStateFlow()

    val signUpError = signUpErrorUseCase()
        .mergeWith(isPasswordErrorFlow)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            false
        )

    val registrationComplete = getRegistrationCompleteUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            false
        )

    fun changeImagePath(imagePath: String) {
        _imagePath.value = imagePath
    }

    fun updateNickName(nickName: String) {
        _nickNameTextState.value = nickName
    }

    fun updateEmail(email: String) {
        _emailTextState.value = email
    }

    fun updatePassword(password: String) {
        _passwordTextState.value = password
    }

    fun updateRepeatedPassword(repeatedPassword: String) {
        _repeatedPasswordTextState.value = repeatedPassword
    }

    fun refreshEmailError() {
        _isEmailError.value = false
    }

    fun refreshPasswordError() {
        viewModelScope.launch(errorHandler) {
            isPasswordErrorFlow.emit(false)
        }
        _isPasswordNotEqualsError.value = true
        _isPasswordError.value = PasswordValidationState(
            hasMinimum = true,
            hasCapitalizedLetter = true,
            successful = true
        )
    }

    fun isEmailValid(): Boolean {
        _isEmailError.value = !Patterns.EMAIL_ADDRESS.matcher(emailTextState.value.trim()).matches()
        return Patterns.EMAIL_ADDRESS.matcher(emailTextState.value).matches()
    }

    fun isPasswordValid(): Boolean {
        val passwordState = validatePassword.execute(passwordTextState.value.trim())
        _isPasswordError.value = passwordState
        return passwordState.successful
    }

    fun isPasswordEquals(): Boolean {
        _isPasswordNotEqualsError.value =
            passwordTextState.value == repeatedPasswordTextState.value && passwordTextState.value.isNotEmpty()
        return passwordTextState.value == repeatedPasswordTextState.value && passwordTextState.value.isNotEmpty()
    }


    fun signUpUser() {
        viewModelScope.launch(errorHandler) {
            signUp(
                User(
                    username = nickNameTextState.value.ifBlank {
                        emailTextState.value.substringBefore("@")
                    },
                    userImagePath = imagePath.value,
                    password = passwordTextState.value,
                    email = emailTextState.value
                )
            )
        }
    }

}