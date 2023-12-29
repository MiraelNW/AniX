package com.miraeldev.account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.account.domain.GetUserInfoUseCase
import com.miraeldev.account.domain.LogOutUseCase
import com.miraeldev.account.domain.UserModel
import com.miraeldev.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    val userInfo = getUserInfoUseCase()
        .map {
            UserModel(
                id = it.id,
                username = it.username,
                name = it.name,
                image = it.image,
                email = it.email
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, UserModel.Empty as UserModel)

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
        }
    }

}