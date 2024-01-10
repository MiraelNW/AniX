package com.miraeldev.forgotpassword.presentation.emailChooseScreen

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.forgotpassword.domain.usecases.CheckEmailExistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailChooseViewModel @Inject constructor(
    private val checkEmailExistUseCase: CheckEmailExistUseCase
) : ViewModel() {

    private var _email = mutableStateOf("")
    val email: State<String> = _email

    private val _isEmailError = MutableStateFlow(false)
    val isEmailError: StateFlow<Boolean> = _isEmailError.asStateFlow()

    private val _isEmailCheckComplete = MutableStateFlow(false)
    val isEmailCheckComplete: StateFlow<Boolean> = _isEmailCheckComplete.asStateFlow()

    fun updateEmailText(value: String) {
        _email.value = value
    }

    fun refreshEmailError() {
        _isEmailError.value = false
    }

    private fun isEmailValid(): Boolean {
        _isEmailError.value = !Patterns.EMAIL_ADDRESS.matcher(email.value.trim()).matches()
        return Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }

    fun checkEmailExist() {
        if (isEmailValid()) {
            viewModelScope.launch {
                _isEmailCheckComplete.value = checkEmailExistUseCase(email.value)
            }
        } else {
            _isEmailError.value = true
        }
    }
}