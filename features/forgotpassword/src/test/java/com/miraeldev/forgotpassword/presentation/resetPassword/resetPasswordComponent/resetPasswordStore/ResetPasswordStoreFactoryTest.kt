package com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.resetPasswordStore

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.miraeldev.MainDispatcherRule
import com.miraeldev.forgotpassword.domain.usecases.SaveNewPasswordUseCase
import com.miraeldev.signin.domain.model.ResetPasswordErrorModel
import com.miraeldev.utils.ValidatePassword
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ResetPasswordStoreFactoryTest {
    @get:Rule
    val rule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val storeFactory = DefaultStoreFactory()
    private val validatePassword: ValidatePassword = ValidatePassword()
    private val saveNewPasswordUseCase: SaveNewPasswordUseCase = mockk(relaxed = true)

    private fun store(): ResetPasswordStore = ResetPasswordStoreFactory(
        storeFactory,
        validatePassword,
        saveNewPasswordUseCase,
    ).create()

    @Test
    fun checkOnPasswordChangedChanged() {
        val password = "password"
        val store = store()

        store.accept(ResetPasswordStore.Intent.OnPasswordChange(password))

        TestCase.assertEquals(password, store.state.password)
    }

    @Test
    fun checkOnRepeatedPasswordChangedChanged() {
        val repeatedPassword = "password"
        val store = store()

        store.accept(ResetPasswordStore.Intent.OnRepeatedPasswordChange(repeatedPassword))

        TestCase.assertEquals(repeatedPassword, store.state.repeatedPassword)
    }

    @Test
    fun checkOnBackClickEvent() = runTest {
        val store = store()

        turbineScope {
            store.labels.test {
                store.accept(ResetPasswordStore.Intent.OnBackClicked)

                TestCase.assertEquals(
                    ResetPasswordStore.Label.OnBackClicked,
                    awaitItem()
                )
            }
        }
    }

    @Test
    fun checkRefreshRepeatedPasswordErrorEvent() = runTest {
        val store = store()
        val password = "Password"
        val repeatedPassword = "password"
        val passwordError = ResetPasswordErrorModel(repeatedPasswordError = true)
        val noPasswordError = ResetPasswordErrorModel()

        resetPassword(store, password, repeatedPassword)

        TestCase.assertEquals(passwordError, store.state.resetPasswordErrorModel)

        store.accept(ResetPasswordStore.Intent.RefreshRepeatedPasswordError)

        TestCase.assertEquals(noPasswordError, store.state.resetPasswordErrorModel)
    }

    @Test
    fun checkRefreshPasswordErrorEvent() = runTest {
        val store = store()
        val password = "password"
        val repeatedPassword = "password"
        val passwordError = ResetPasswordErrorModel(
            passwordError = true,
            passwordHasCapitalizedLetterError = true
        )
        val noPasswordError = ResetPasswordErrorModel()

        resetPassword(store, password, repeatedPassword)

        TestCase.assertEquals(passwordError, store.state.resetPasswordErrorModel)

        store.accept(ResetPasswordStore.Intent.RefreshPasswordError)

        TestCase.assertEquals(noPasswordError, store.state.resetPasswordErrorModel)
    }

    @Test
    fun checkSignUpWrongRepeatedPasswordEvent() = runTest {
        val store = store()

        val wrongEmail = "email@mail.ru"
        val password = "Password"
        val repeatedPassword = "password"
        val repeatedPasswordError = ResetPasswordErrorModel(repeatedPasswordError = true)

        store.accept(ResetPasswordStore.Intent.SaveNewPassword(wrongEmail, password, repeatedPassword))

        coVerify(exactly = 0) { saveNewPasswordUseCase(wrongEmail, password) }

        TestCase.assertEquals(repeatedPasswordError, store.state.resetPasswordErrorModel)
    }

    @Test
    fun checkSignUpWrongPasswordWrongLengthEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val wrongPassword = "L"
        val repeatedPassword = "L"
        val passwordError = ResetPasswordErrorModel(passwordError = true, passwordLengthError = true)

        store.accept(ResetPasswordStore.Intent.SaveNewPassword(email, wrongPassword, repeatedPassword))

        coVerify(exactly = 0) { saveNewPasswordUseCase(email, wrongPassword) }

        TestCase.assertEquals(passwordError, store.state.resetPasswordErrorModel)
    }

    @Test
    fun checkSignUpWrongPasswordNoCapitalizedLetterEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val wrongPassword = "password"
        val repeatedPassword = "password"
        val passwordError = ResetPasswordErrorModel(passwordError = true, passwordHasCapitalizedLetterError = true)

        store.accept(ResetPasswordStore.Intent.SaveNewPassword(email, wrongPassword, repeatedPassword))

        coVerify(exactly = 0) { saveNewPasswordUseCase(email, wrongPassword) }

        TestCase.assertEquals(passwordError, store.state.resetPasswordErrorModel)
    }

    @Test
    fun checkSignUpWrongPasswordEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val wrongPassword = "g"
        val repeatedPassword = "g"
        val passwordError = ResetPasswordErrorModel(
            passwordError = true,
            passwordHasCapitalizedLetterError = true,
            passwordLengthError = true
        )

        store.accept(ResetPasswordStore.Intent.SaveNewPassword(email, wrongPassword, repeatedPassword))

        coVerify(exactly = 0) { saveNewPasswordUseCase(email, wrongPassword) }

        TestCase.assertEquals(passwordError, store.state.resetPasswordErrorModel)
    }

    @Test
    fun checkSignUpServerErrorEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val password = "Password"
        val repeatedPassword = "Password"
        val passwordError = ResetPasswordErrorModel(networkError = true)

        coEvery { saveNewPasswordUseCase(email, password) } returns false
        store.accept(ResetPasswordStore.Intent.SaveNewPassword(email, password, repeatedPassword))

        coVerify { saveNewPasswordUseCase(email, password) }
        TestCase.assertEquals(passwordError, store.state.resetPasswordErrorModel)
    }

    private fun resetPassword(store: ResetPasswordStore, password: String, repeatedPassword: String) {
        store.accept(ResetPasswordStore.Intent.SaveNewPassword("email", password, repeatedPassword))
    }
}