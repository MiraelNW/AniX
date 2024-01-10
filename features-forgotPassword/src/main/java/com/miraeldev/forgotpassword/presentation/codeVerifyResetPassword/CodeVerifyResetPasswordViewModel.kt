package com.miraeldev.forgotpassword.presentation.codeVerifyResetPassword

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.forgotpassword.domain.usecases.SendNewOtpUseCase
import com.miraeldev.forgotpassword.domain.usecases.VerifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodeVerifyResetPasswordViewModel @Inject constructor(
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val sendNewOtpUseCase: SendNewOtpUseCase
) : ViewModel() {

    private var _otpText = mutableStateOf("")
    val otpText: State<String> = _otpText

    private val _isOtpCorrect = MutableStateFlow(false)
    val isOtpCorrect = _isOtpCorrect.asStateFlow()

    fun updateOtpText(value: String) {
        _otpText.value = value
    }

    fun verifyOtpCode() {
        viewModelScope.launch {
            _isOtpCorrect.value = verifyOtpUseCase(otpText.value)
        }
    }

    fun sendNewOtpCode() {
        viewModelScope.launch {
            sendNewOtpUseCase()
        }
    }
}