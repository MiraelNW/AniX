package com.miraelDev.vauma.presentation.mainScreen

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.data.dataStore.PreferenceManager
import com.miraelDev.vauma.domain.models.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
       private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val isDarkThemeKey = booleanPreferencesKey(IS_DARK_THEME)

    private val _isDarkThemeFlow = MutableStateFlow(false)
    val isDarkThemeFlow = _isDarkThemeFlow.asStateFlow()

    val authState = MutableStateFlow(AuthState.Authorized as AuthState)

    fun changeAuthState(){
        authState.value = AuthState.Authorized
    }

    init {
        viewModelScope.launch {

            preferenceManager.getPreference(isDarkThemeKey, false).collectLatest { isDark ->
                _isDarkThemeFlow.value = isDark
                _isLoading.value = false
            }
        }
    }

    fun setThemeMode(isDarkTheme:Boolean){
        viewModelScope.launch {
            preferenceManager.putPreference(isDarkThemeKey, isDarkTheme)
        }
    }

    companion object {
        private const val IS_DARK_THEME = "is_dark_theme"
    }

}