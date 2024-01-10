package com.miraelDev.vauma.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.domain.usecases.CheckUserAuthStateUseCase
import com.miraelDev.vauma.domain.usecases.GetDarkThemeUseCase
import com.miraelDev.vauma.domain.usecases.GetUserAuthStateUseCase
import com.miraelDev.vauma.domain.usecases.SetDarkThemeUseCase
import com.miraeldev.models.auth.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
    private val getUserAuthStateUseCase: GetUserAuthStateUseCase,
    private val getDarkThemeUseCase: GetDarkThemeUseCase,
    private val checkUserAuthStateUseCase: CheckUserAuthStateUseCase
) : ViewModel() {

    private val _isDarkThemeFlow = MutableStateFlow(false)
    val isDarkThemeFlow = _isDarkThemeFlow.asStateFlow()

    val authState = getUserAuthStateUseCase()
        .stateIn(
            viewModelScope, SharingStarted.Lazily, AuthState.Initial
        )

    init {
        viewModelScope.launch {
            checkUserAuthStateUseCase()
            _isDarkThemeFlow.value = getDarkThemeUseCase()
        }
    }

    fun setThemeMode(isDarkTheme: Boolean) {
        viewModelScope.launch {
            setDarkThemeUseCase(isDarkTheme)
        }
    }

}