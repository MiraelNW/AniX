package com.miraeldev.animelist.presentation.home

import com.miraeldev.user.User

sealed class HomeScreenState {

    data object Loading : HomeScreenState()

    data object Initial : HomeScreenState()

    data class Success(val result: User) : HomeScreenState()

}