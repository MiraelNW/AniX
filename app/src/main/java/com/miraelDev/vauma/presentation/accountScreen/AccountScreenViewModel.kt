package com.miraelDev.vauma.presentation.accountScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.domain.usecases.userUseCase.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
        }
    }

}