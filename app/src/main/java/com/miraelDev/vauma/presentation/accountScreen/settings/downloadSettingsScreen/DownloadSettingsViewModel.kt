package com.miraelDev.vauma.presentation.accountScreen.settings.downloadSettingsScreen

import android.os.Environment
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.data.dataStore.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class DownloadSettingsViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val isWifiOnlyKey = booleanPreferencesKey(IS_WIFI_ONLY_KEY)

    private val _isSelected = mutableStateOf(true)
    val isSelected: State<Boolean> = _isSelected

    init{
        viewModelScope.launch {
            preferenceManager.getPreference(isWifiOnlyKey,true).collectLatest {
                _isSelected.value = it
            }
        }
    }
    fun changeStatus() {
        _isSelected.value = !_isSelected.value
        viewModelScope.launch {
            preferenceManager.putPreference(isWifiOnlyKey, isSelected.value)
        }
    }

    fun deleteAllVideos(){
        val filePath = Environment.getExternalStorageDirectory().absolutePath + "/Download/Vauma"
        val file = File(filePath)
        file.deleteRecursively()
    }

    companion object {
        private const val IS_WIFI_ONLY_KEY = "is_wifi_only_key"
    }

}