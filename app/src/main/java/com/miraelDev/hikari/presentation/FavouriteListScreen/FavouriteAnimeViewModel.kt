package com.miraelDev.hikari.presentation.FavouriteListScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteAnimeViewModel @Inject constructor(

) : ViewModel() {

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState

    fun updateSearchTextState(value: String) {
        _searchTextState.value = value
    }

}