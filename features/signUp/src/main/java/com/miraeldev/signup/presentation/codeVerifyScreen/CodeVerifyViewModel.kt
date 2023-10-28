package com.miraeldev.signup.presentation.codeVerifyScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.signup.domain.useCases.SignUpUseCase
import com.miraeldev.signup.domain.useCases.UpdateUserUseCase
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
            signUpUseCase(password = "Vauma1234", email = email)
            updateUserUseCase(email)
        }
    }

    fun sendNewOtpCode() {
        Log.d("tag", "send new otp")
        // verify otp code
    }
}