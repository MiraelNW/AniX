package com.miraeldev.signup.presentation.codeVerifyScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.signup.domain.useCases.UpdateUserUseCase
import com.miraeldev.signup.domain.useCases.VerifyOtpCodeUseCase
import com.miraeldev.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodeVerifyViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val verifyOtpCodeUseCase: VerifyOtpCodeUseCase
) : ViewModel() {

    private var _otpText = mutableStateOf("")
    val otpText: State<String> = _otpText

    fun updateOtpText(value: String) {
        _otpText.value = value
    }

    fun verifyOtpCode(otpCode: String, email: String, password: String) {
        viewModelScope.launch {
            verifyOtpCodeUseCase(
                otpCode,
                User(email = email, password = password, username = email.substringBefore("@"))
            )
        }
    }

    fun updateUser(email: String) {
        viewModelScope.launch {
            updateUserUseCase(email)
        }
    }

    fun sendNewOtpCode() {
        Log.d("tag", "send new otp")
        // verify otp code
    }
}