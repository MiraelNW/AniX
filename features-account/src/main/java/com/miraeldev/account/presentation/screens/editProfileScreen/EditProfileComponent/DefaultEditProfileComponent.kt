package com.miraeldev.account.presentation.screens.editProfileScreen.EditProfileComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.OnBackPressed
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultEditProfileComponentFactory = (ComponentContext, OnBackPressed) -> DefaultEditProfileComponent

@Inject
class DefaultEditProfileComponent(
    private val editProfileStore: EditProfileStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onBackClicked: () -> Unit
) : EditProfileComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { editProfileStore.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is EditProfileStore.Label.OnBackClick -> onBackClicked()

                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<EditProfileStore.State> = store.stateFlow

    override fun onChangeImage(image: String) {
        store.accept(EditProfileStore.Intent.OnChangeImage(image))
    }

    override fun onChangeUsername(username: String) {
        store.accept(EditProfileStore.Intent.OnChangeUsername(username))
    }

    override fun onChangeEmail(email: String) {
        store.accept(EditProfileStore.Intent.OnChangeEmail(email))
    }

    override fun onChangeCurrentPassword(currentPassword: String) {
        store.accept(EditProfileStore.Intent.OnChangeCurrentPassword(currentPassword))
    }

    override fun onChangePassword(password: String) {
        store.accept(EditProfileStore.Intent.OnChangePassword(password))
    }

    override fun onChangeRepeatedPassword(repeatedPassword: String) {
        store.accept(EditProfileStore.Intent.OnChangeRepeatedPassword(repeatedPassword))
    }

    override fun resetAllChanges() {
        store.accept(EditProfileStore.Intent.ResetAllChanges)
    }

    override fun onChangePasswordClick(
        currentPassword: String,
        password: String,
        repeatedPassword: String
    ) {
        store.accept(
            EditProfileStore.Intent.OnChangePasswordClick(
                currentPassword,
                password,
                repeatedPassword
            )
        )
    }

    override fun onBackClick() {
        store.accept(EditProfileStore.Intent.OnBackClick)
    }

    override fun refreshPasswordError() {
        store.accept(EditProfileStore.Intent.RefreshPasswordError)
    }

    override fun refreshRepeatedPasswordError() {
        store.accept(EditProfileStore.Intent.RefreshRepeatedPasswordError)
    }

    override fun refreshEmailError() {
        store.accept(EditProfileStore.Intent.RefreshEmailError)
    }

    override fun updateUserInfo(image: String,email: String,username: String) {
        store.accept(EditProfileStore.Intent.UpdateUserInfo(image, email, username))
    }
}