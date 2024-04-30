package com.miraeldev.account.presentation.screens.editProfileScreen.editProfileComponent

import kotlinx.coroutines.flow.StateFlow

interface EditProfileComponent {

    val model: StateFlow<EditProfileStore.State>

    fun onChangeImage(image: String)
    fun onChangeUsername(username: String)
    fun onChangeEmail(email: String)
    fun onChangeCurrentPassword(currentPassword: String)
    fun onChangePassword(password: String)
    fun onChangeRepeatedPassword(repeatedPassword: String)
    fun onChangePasswordClick(
        currentPassword: String,
        password: String,
        repeatedPassword: String
    )
    fun resetAllChanges()
    fun onBackClick()
    fun refreshPasswordError()
    fun refreshRepeatedPasswordError()
    fun refreshEmailError()
    fun updateUserInfo(image: String, email: String, username: String)
}