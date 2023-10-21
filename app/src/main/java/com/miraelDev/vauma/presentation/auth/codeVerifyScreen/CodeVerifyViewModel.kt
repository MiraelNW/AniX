package com.miraelDev.vauma.presentation.auth.codeVerifyScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.domain.models.user.LocalUser
import com.miraelDev.vauma.domain.models.user.User
import com.miraelDev.vauma.domain.usecases.authUseCases.SignUpUseCase
import com.miraelDev.vauma.domain.usecases.userUseCase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodeVerifyViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private var _otpText = mutableStateOf("")
    val otpText: State<String> = _otpText

    fun updateOtpText(value: String) {
        _otpText.value = value
    }

    fun verifyOtpCode() {

    }

    fun updateUser(email: String) {
        viewModelScope.launch {
            signUpUseCase(User(password = "Vauma1234", email = email))
            updateUserUseCase(LocalUser(email))
        }
    }

    fun sendNewOtpCode() {
        Log.d("tag", "send new otp")
        // verify otp code
    }
}