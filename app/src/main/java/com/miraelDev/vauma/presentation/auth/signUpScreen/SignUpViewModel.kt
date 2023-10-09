package com.miraelDev.vauma.presentation.auth.signUpScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.domain.models.user.User
import com.miraelDev.vauma.domain.usecases.authUseCases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registration: SignUpUseCase
) : ViewModel() {

    private val _nickNameState = mutableStateOf("")
    val nickNameState: State<String> = _nickNameState

    private val _emailState = mutableStateOf("")
    val emailState: State<String> = _emailState

    private val _passwordState = mutableStateOf("")
    val passwordState: State<String> = _passwordState

    private val _repeatedPasswordState = mutableStateOf("")
    val repeatedPasswordState: State<String> = _repeatedPasswordState

    private val _phoneNumberState = mutableStateOf("")
    val phoneNumberState: State<String> = _phoneNumberState

    fun updateNickName(nickName: String) {
        _nickNameState.value = nickName
    }

    fun updateEmail(email: String) {
        _emailState.value = email
    }

    fun updatePassword(password: String) {
        _passwordState.value = password
    }

    fun updateRepeatedPassword(repeatedPassword: String) {
        _repeatedPasswordState.value = repeatedPassword
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _phoneNumberState.value = phoneNumber
    }

    fun passwordIsEqual(): Boolean {
        return _repeatedPasswordState.value == _passwordState.value
    }

    fun registrationUser() {
        viewModelScope.launch {
            registration(
                User(
                    username = nickNameState.value.ifBlank { emailState.value.substringBefore("@") },
                    password = passwordState.value,
                    email = emailState.value
                )
            )
        }
    }

}