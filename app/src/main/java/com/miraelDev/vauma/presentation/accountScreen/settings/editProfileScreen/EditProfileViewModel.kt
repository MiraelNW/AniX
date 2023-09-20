package com.miraelDev.vauma.presentation.accountScreen.settings.editProfileScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(): ViewModel() {

    private val _nickNameState = mutableStateOf("")
    val nickNameState : State<String> = _nickNameState

    private val _emailState = mutableStateOf("")
    val emailState : State<String> = _emailState

    fun updateNickName(nickName:String){
        _nickNameState.value = nickName
    }

    fun updateEmail(email:String){
        _emailState.value = email
    }

}