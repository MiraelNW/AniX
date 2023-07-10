package com.miraelDev.anix.presentation.FavouriteListScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class FavouriteAnimeViewModel @Inject constructor(

) : ViewModel() {

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState

    fun updateSearchTextState(value: String) {
        _searchTextState.value = value
    }

}