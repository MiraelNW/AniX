package com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.emailChooseStore.EmailChooseStore
import com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.emailChooseStore.EmailChooseStoreFactory
import com.miraeldev.models.OnBackPressed
import com.miraeldev.models.OnEmailExist
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultEmailChooseComponentFactory = (ComponentContext, OnBackPressed, OnEmailExist) -> DefaultEmailChooseComponent

@Inject
class DefaultEmailChooseComponent(
    private val storeFactory: EmailChooseStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onBackClicked: () -> Unit,
    @Assisted onEmailExist: (String) -> Unit
) : EmailChooseComponent, ComponentContext by componentContext {

    private val store: EmailChooseStore = instanceKeeper.getStore { storeFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is EmailChooseStore.Label.OnEmailExist -> {
                        onEmailExist(it.email)
                    }

                    EmailChooseStore.Label.OnBackClicked -> {
                        onBackClicked()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<EmailChooseStore.State>
        get() = store.stateFlow

    override fun onEmailChange(email: String) {
        store.accept(EmailChooseStore.Intent.OnEmailChange(email))
    }

    override fun refreshEmailError() {
        store.accept(EmailChooseStore.Intent.RefreshEmailError)
    }

    override fun checkEmailExist(email: String) {
        store.accept(EmailChooseStore.Intent.CheckEmailExist(email))
    }

    override fun onBackClicked() {
        store.accept(EmailChooseStore.Intent.OnBackClicked)
    }
}