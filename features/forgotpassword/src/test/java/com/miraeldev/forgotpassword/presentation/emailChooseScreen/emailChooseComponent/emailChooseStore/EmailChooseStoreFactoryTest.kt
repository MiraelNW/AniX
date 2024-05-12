package com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.emailChooseStore

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.miraeldev.MainDispatcherRule
import com.miraeldev.forgotpassword.domain.models.EmailChooseErrorModel
import com.miraeldev.forgotpassword.domain.usecases.CheckEmailExistUseCase
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class EmailChooseStoreFactoryTest {

    @get:Rule
    val rule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val storeFactory = DefaultStoreFactory()

    private val checkEmailExistUseCase: CheckEmailExistUseCase = mockk(relaxed = true)

    private fun store(): EmailChooseStore = EmailChooseStoreFactory(
        storeFactory,
        checkEmailExistUseCase,
    ).create()

    @Test
    fun checkOnEmailChangedChanged() {
        val email = "email"
        val store = store()

        store.accept(EmailChooseStore.Intent.OnEmailChange(email))

        TestCase.assertEquals(email, store.state.email)
    }

    @Test
    fun checkOnBackClickEvent() = runTest {
        val store = store()

        turbineScope {
            store.labels.test {
                store.accept(EmailChooseStore.Intent.OnBackClicked)

                TestCase.assertEquals(
                    EmailChooseStore.Label.OnBackClicked,
                    awaitItem()
                )
            }
        }
    }

    @Test
    fun checkRefreshEmailErrorEvent() = runTest {
        val store = store()
        val email = "email"
        val emailError = EmailChooseErrorModel(emailNotValidError = true)
        val noEmailError = EmailChooseErrorModel()

        checkEmailExist(store, email)

        TestCase.assertEquals(emailError, store.state.emailChooseErrorModel)

        store.accept(EmailChooseStore.Intent.RefreshEmailError)

        TestCase.assertEquals(noEmailError, store.state.emailChooseErrorModel)
    }

    @Test
    fun checkRefreshEmailNetworkErrorEvent() = runTest {
        val store = store()
        val email = "email@mail.ru"
        coEvery { checkEmailExistUseCase(email) } returns false
        val emailError = EmailChooseErrorModel(emailNotExistError = true)

        checkEmailExist(store, email)

        TestCase.assertEquals(emailError, store.state.emailChooseErrorModel)
    }

    @Test
    fun checkRefreshEmailSuccessEvent() = runTest {
        val store = store()
        val email = "email@mail.ru"
        coEvery { checkEmailExistUseCase(email) } returns true

        turbineScope {
            store.labels.test {
                checkEmailExist(store, email)

                TestCase.assertEquals(
                    EmailChooseStore.Label.OnEmailExist(email),
                    awaitItem()
                )
            }
        }
    }

    private fun checkEmailExist(store: EmailChooseStore, email: String) {
        store.accept(EmailChooseStore.Intent.CheckEmailExist(email))
    }
}