package com.miraelDev.vauma.presentation.auth.signInScreen

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.miraelDev.vauma.domain.models.user.PasswordValidationState
import com.miraelDev.vauma.domain.models.user.User
import com.miraelDev.vauma.domain.usecases.authUseCases.CheckVkAuthStateUseCase
import com.miraelDev.vauma.domain.usecases.authUseCases.GetSignInErrorUseCase
import com.miraelDev.vauma.domain.usecases.authUseCases.SignInUseCase
import com.miraelDev.vauma.domain.usecases.userUseCase.GetUserUseCase
import com.miraelDev.vauma.exntensions.mergeWith
import com.miraelDev.vauma.presentation.auth.utils.ValidatePassword
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
    private val getUserUseCase: GetUserUseCase,
    private val checkVkAuthStateUseCase: CheckVkAuthStateUseCase,
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
//        .mergeWith(isPasswordErrorFlow)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            false
        )

    init {
        viewModelScope.launch {
            updateLoginTextState(getUserUseCase().email)
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
            signInUseCase(User(email = loginTextState.value, password = passwordTextState.value))
        }
    }

    fun signInGoogleAccount(account: GoogleSignInAccount) {
    }

    fun signInVkAccount() {
        viewModelScope.launch {
            checkVkAuthStateUseCase()
        }
    }

}