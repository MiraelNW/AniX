package com.miraelDev.vauma.presentation.auth.signUpScreen

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.domain.models.user.User
import com.miraelDev.vauma.domain.usecases.authUseCases.SignUpUseCase
import com.miraelDev.vauma.presentation.auth.utils.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUp: SignUpUseCase,
    private val validatePassword: ValidatePassword
) : ViewModel() {

    private val _nickNameTextState = mutableStateOf("")
    val nickNameTextState: State<String> = _nickNameTextState

    private val _emailTextState = mutableStateOf("")
    val emailTextState: State<String> = _emailTextState

    private val _passwordTextState = mutableStateOf("")
    val passwordTextState: State<String> = _passwordTextState

    private val _repeatedPasswordTextState = mutableStateOf("")
    val repeatedPasswordTextState: State<String> = _repeatedPasswordTextState

    private val _phoneNumberTextState = mutableStateOf("")
    val phoneNumberTextState: State<String> = _phoneNumberTextState

    private val _isPasswordError = MutableStateFlow(false)
    val isPasswordError: StateFlow<Boolean> = _isPasswordError.asStateFlow()

    private val _isEmailError = MutableStateFlow(false)
    val isEmailError: StateFlow<Boolean> = _isEmailError.asStateFlow()

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

    fun updatePhoneNumber(phoneNumber: String) {
        _phoneNumberTextState.value = phoneNumber
    }

    fun refreshEmailError() {
        _isEmailError.value = false
    }

    fun refreshPasswordError() {
        _isPasswordError.value = false
    }

    fun isEmailValid(): Boolean {
        _isEmailError.value = !Patterns.EMAIL_ADDRESS.matcher(emailTextState.value).matches()
        return Patterns.EMAIL_ADDRESS.matcher(emailTextState.value).matches()
    }

    fun isPasswordValid(): Boolean {
        if(repeatedPasswordTextState.value != passwordTextState.value){
            _isPasswordError.value = true
            return false
        }
        val passwordState = validatePassword.execute(passwordTextState.value)
        _isPasswordError.value = !passwordState.successful
        return passwordState.successful
    }

    fun signUpUser() {
        viewModelScope.launch {
            signUp(
                User(
                    username = nickNameTextState.value.ifBlank { emailTextState.value.substringBefore("@") },
                    password = passwordTextState.value,
                    email = emailTextState.value
                )
            )
        }
    }

}