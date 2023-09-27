package com.miraelDev.vauma.presentation.auth.signInScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

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

}