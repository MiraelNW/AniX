package com.miraeldev.signin.presentation

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.exntensions.mergeWith
import com.miraeldev.signin.domain.LoginWithVkUseCase
import com.miraeldev.signin.domain.GetSignInErrorUseCase
import com.miraeldev.signin.domain.GetUserEmailUseCase
import com.miraeldev.signin.domain.LogInWithGoogleUseCase
import com.miraeldev.signin.domain.SignInUseCase
import com.miraeldev.utils.PasswordValidationState
import com.miraeldev.utils.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInErrorUseCase: GetSignInErrorUseCase,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val loginWithVkUseCase: LoginWithVkUseCase,
    private val logInWithGoogleUseCase: LogInWithGoogleUseCase,
    private val validatePassword: ValidatePassword
) : ViewModel() {

    private val _loginTextState = mutableStateOf("")
    val loginTextState: State<String> = _loginTextState

    private val _passwordTextState = mutableStateOf("")
    val passwordTextState: State<String> = _passwordTextState

    private val isPasswordErrorFlow = MutableSharedFlow<Boolean>()

    private val _isPasswordError = MutableStateFlow(PasswordValidationState())
    val isPasswordError = _isPasswordError.asStateFlow()

    private val _isEmailError = MutableStateFlow(false)
    val isEmailError: StateFlow<Boolean> = _isEmailError.asStateFlow()

    val signInError = signInErrorUseCase()
        .mergeWith(isPasswordErrorFlow)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            false
        )

    init {
        viewModelScope.launch {
            updateLoginTextState(getUserEmailUseCase())
        }

    }

    fun updateLoginTextState(value: String) {
        _loginTextState.value = value
    }

    fun updatePasswordTextState(value: String) {
        _passwordTextState.value = value
    }

    fun refreshEmailError() {
        _isEmailError.value = false
    }

    fun refreshPasswordError() {
        viewModelScope.launch {
            isPasswordErrorFlow.emit(false)
        }
        _isPasswordError.value = PasswordValidationState(
            hasMinimum = true,
            hasCapitalizedLetter = true,
            successful = true
        )
    }

    fun isEmailValid(): Boolean {
        _isEmailError.value = !Patterns.EMAIL_ADDRESS.matcher(loginTextState.value.trim()).matches()
        return Patterns.EMAIL_ADDRESS.matcher(loginTextState.value).matches()
    }

    fun isPasswordValid(): Boolean {
        val passwordState = validatePassword.execute(passwordTextState.value.trim())
        _isPasswordError.value = passwordState
        return passwordState.successful
    }

    fun signIn() {
        viewModelScope.launch {
            signInUseCase(
                username = loginTextState.value.substringBefore("@"),
                password = passwordTextState.value
            )
        }
    }

    fun signInGoogleAccount(idToken: String) {
        viewModelScope.launch {
            logInWithGoogleUseCase(idToken)
        }
    }

    fun signInVkAccount(accessToken:String,userId:String,email:String?) {
        viewModelScope.launch {
            loginWithVkUseCase(accessToken,userId,email)
        }
    }

}