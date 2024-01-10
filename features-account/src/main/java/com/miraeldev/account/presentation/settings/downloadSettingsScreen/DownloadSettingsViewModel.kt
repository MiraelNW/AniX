package com.miraeldev.account.presentation.settings.downloadSettingsScreen

import android.os.Environment
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.account.domain.GetPreferenceUseCase
import com.miraeldev.account.domain.SetPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class DownloadSettingsViewModel @Inject constructor(
    private val setPreferenceUseCase: SetPreferenceUseCase,
    private val getPreferenceUseCase: GetPreferenceUseCase,
) : ViewModel() {

    private val _isSelected = mutableStateOf(true)
    val isSelected: State<Boolean> = _isSelected

    init {
        viewModelScope.launch {
            _isSelected.value = getPreferenceUseCase(IS_WIFI_ONLY_KEY)
        }
    }

    fun changeStatus() {
        _isSelected.value = !_isSelected.value
        viewModelScope.launch {
            setPreferenceUseCase(IS_WIFI_ONLY_KEY, isSelected.value)
        }
    }

    fun deleteAllVideos() {
        val filePath = Environment.getExternalStorageDirectory().absolutePath + "/Download/Vauma"
        val file = File(filePath)
        file.deleteRecursively()
    }

    companion object {
        private const val IS_WIFI_ONLY_KEY = "is_wifi_only_key"
    }

}