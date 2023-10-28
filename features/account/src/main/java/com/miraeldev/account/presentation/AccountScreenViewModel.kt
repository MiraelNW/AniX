package com.miraeldev.account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.account.domain.LogOutUseCase
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