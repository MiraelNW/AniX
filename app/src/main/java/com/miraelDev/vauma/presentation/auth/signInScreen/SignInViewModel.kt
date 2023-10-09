package com.miraelDev.vauma.presentation.auth.signInScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.domain.models.user.User
import com.miraelDev.vauma.domain.usecases.authUseCases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _loginTextState = mutableStateOf("")
    val loginTextState: State<String> = _loginTextState

    private val _passwordTextState = mutableStateOf("")
    val passwordTextState: State<String> = _passwordTextState

    fun updateLoginTextState(value: String) {
        _loginTextState.value = value
    }

    fun updatePasswordTextState(value: String) {
        _passwordTextState.value = value
    }

    fun signIn(login: String, password: String) {
        viewModelScope.launch {
            signInUseCase(User(email = login, password = password))
        }
    }

}