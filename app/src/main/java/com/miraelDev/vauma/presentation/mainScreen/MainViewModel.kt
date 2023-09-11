package com.miraelDev.vauma.presentation.mainScreen

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.data.dataStore.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
       private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val mutableStateFlow = MutableStateFlow(true)
    val isLoading = mutableStateFlow.asStateFlow()

    private val isDarkThemeKey = booleanPreferencesKey(IS_DARK_THEME)

    private val _isDarkThemeFlow = MutableStateFlow(false)
    val isDarkThemeFlow = _isDarkThemeFlow.asStateFlow()

    init {
        viewModelScope.launch {
            preferenceManager.getPreference(isDarkThemeKey, false).collectLatest { isDark ->
                _isDarkThemeFlow.value = isDark
                mutableStateFlow.value = false
            }
        }
    }

    fun setThemeMode(isDarkTheme:Boolean){
        viewModelScope.launch {
            preferenceManager.putPreference(isDarkThemeKey, isDarkTheme)
        }
    }

    companion object {
        private val IS_DARK_THEME = "is_dark_theme"
    }

}