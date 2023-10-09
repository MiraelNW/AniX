package com.miraelDev.vauma.presentation.mainScreen

import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.data.dataStore.PreferenceManager
import com.miraelDev.vauma.domain.models.auth.AuthState
import com.miraelDev.vauma.domain.usecases.authUseCases.CheckUserAuthStateUseCase
import com.miraelDev.vauma.domain.usecases.authUseCases.GetUserAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val getUserAuthStateUseCase: GetUserAuthStateUseCase,
    private val checkUserAuthStateUseCase: CheckUserAuthStateUseCase
) : ViewModel() {

    private val isDarkThemeKey = booleanPreferencesKey(IS_DARK_THEME)

    private val _isDarkThemeFlow = MutableStateFlow(false)
    val isDarkThemeFlow = _isDarkThemeFlow.asStateFlow()

    val authState = getUserAuthStateUseCase()
        .stateIn(
            viewModelScope, SharingStarted.Lazily, AuthState.Initial
        )

    init {
        viewModelScope.launch {
            checkUserAuthStateUseCase()
            preferenceManager.getPreference(isDarkThemeKey, false).collectLatest { isDark ->
                _isDarkThemeFlow.value = isDark
            }
        }
    }

    fun setThemeMode(isDarkTheme: Boolean) {
        viewModelScope.launch {
            preferenceManager.putPreference(isDarkThemeKey, isDarkTheme)
        }
    }

    companion object {
        private const val IS_DARK_THEME = "is_dark_theme"
    }

}