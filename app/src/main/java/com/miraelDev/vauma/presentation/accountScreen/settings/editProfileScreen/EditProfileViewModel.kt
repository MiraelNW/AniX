package com.miraelDev.vauma.presentation.accountScreen.settings.editProfileScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor() : ViewModel() {

    private val _nickNameState = mutableStateOf("")
    val nickNameState: State<String> = _nickNameState

    val isCurrentPasswordInvalid = MutableStateFlow(false)

    private val _isNewPasswordValid = MutableSharedFlow<Boolean>(replay=1)
    val isNewPasswordValid = _isNewPasswordValid.asSharedFlow()

    private val _emailState = mutableStateOf("")
    val emailState: State<String> = _emailState

    private val _currentPassword = mutableStateOf("")
    val currentPassword: State<String> = _currentPassword

    private val _newPassword = mutableStateOf("")
    val newPassword: State<String> = _newPassword

    private val _repeatedPassword = mutableStateOf("")
    val repeatedPassword: State<String> = _repeatedPassword

    fun updateNickName(nickName: String) {
        _nickNameState.value = nickName
    }

    fun updateEmail(email: String) {
        _emailState.value = email
    }

    fun updateCurrentPassword(currPassword: String) {
        _currentPassword.value = currPassword
    }

    fun updateNewPassword(newPassword: String) {
        _newPassword.value = newPassword
    }

    fun updateRepeatedPassword(repeatedPassword: String) {
        _repeatedPassword.value = repeatedPassword
    }

    fun resetAllChanges(){
        updateNewPassword("")
        updateCurrentPassword("")
        updateRepeatedPassword("")
    }

}