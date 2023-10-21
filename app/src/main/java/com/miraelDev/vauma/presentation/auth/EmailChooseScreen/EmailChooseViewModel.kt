package com.miraelDev.vauma.presentation.auth.EmailChooseScreen

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.miraelDev.vauma.domain.usecases.authUseCases.SignUpUseCase
import com.miraelDev.vauma.domain.usecases.userUseCase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EmailChooseViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private var _email = mutableStateOf("")
    val email: State<String> = _email

    private val _isEmailError = MutableStateFlow(false)
    val isEmailError: StateFlow<Boolean> = _isEmailError.asStateFlow()

    fun updateOtpText(value: String) {
        _email.value = value
    }

    fun refreshEmailError() {
        _isEmailError.value = false
    }

    fun isEmailValid(): Boolean {
        _isEmailError.value = !Patterns.EMAIL_ADDRESS.matcher(email.value.trim()).matches()
        return Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }
}