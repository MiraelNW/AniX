package com.miraeldev.signin.presentation.signInComponent.store

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.miraeldev.signin.domain.model.SignInErrorModel
import com.miraeldev.signin.domain.usecases.GetUserEmailUseCase
import com.miraeldev.signin.domain.usecases.LogInWithGoogleUseCase
import com.miraeldev.signin.domain.usecases.LoginWithVkUseCase
import com.miraeldev.signin.domain.usecases.SignInUseCase
import com.miraeldev.signin.presentation.store.SignInStore
import com.miraeldev.utils.ValidatePassword
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class SignInStoreFactoryTest {

    @get:Rule
    val rule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val storeFactory = DefaultStoreFactory()

    private val getUserEmailUseCase: GetUserEmailUseCase = mockk(relaxed = true)
    private val signInUseCase: SignInUseCase = mockk(relaxed = true)
    private val loginWithVkUseCase: LoginWithVkUseCase = mockk(relaxed = true)
    private val logInWithGoogleUseCase: LogInWithGoogleUseCase = mockk(relaxed = true)
    private val validatePassword: ValidatePassword = ValidatePassword()

    private fun store(): SignInStore = SignInStoreFactory(
        storeFactory,
        getUserEmailUseCase,
        signInUseCase,
        loginWithVkUseCase,
        logInWithGoogleUseCase,
        validatePassword
    ).create()

    @Test
    fun checkStateAfterSuccessDataLoading() = runTest {
        val email = "email"
        coEvery { getUserEmailUseCase() } returns email

        val store = store()

        TestCase.assertEquals(email, store.state.email)
    }

    @Test
    fun checkChangeEmailEvent() = runTest {
        val email = "email"
        val store = store()

        store.accept(SignInStore.Intent.ChangeEmail(email))

        TestCase.assertEquals(email, store.state.email)
    }

    @Test
    fun checkChangePasswordEvent() = runTest {
        val password = "password"
        val store = store()

        store.accept(SignInStore.Intent.ChangePassword(password))

        TestCase.assertEquals(password, store.state.password)
    }

    @Test
    fun checkAuthViaGoogleEvent() = runTest {
        val store = store()
        val idToken = "123"

        store.accept(SignInStore.Intent.AuthViaGoogle(idToken))

        coVerify { logInWithGoogleUseCase(idToken) }
    }

    @Test
    fun checkAuthViaVkEvent() = runTest {
        val store = store()
        val accessToken = "123"
        val userId = "123"
        val email = "email"

        store.accept(SignInStore.Intent.AuthViaVk(accessToken, userId, email))

        coVerify { loginWithVkUseCase(accessToken, userId, email) }
    }

    @Test
    fun checkRefreshEmailErrorEvent() = runTest {
        val store = store()
        val email = "email"
        val password = "Password"
        val noEmailError = SignInErrorModel(emailError = false)
        val emailError = SignInErrorModel(emailError = true)

        signIn(store, email, password)

        TestCase.assertNotSame(emailError, store.state.signInError)

        store.accept(SignInStore.Intent.RefreshEmailError)

        TestCase.assertEquals(noEmailError, store.state.signInError)
    }

    @Test
    fun checkRefreshPasswordErrorEvent() = runTest {
        val store = store()
        val email = "email@mail.ru"
        val password = "password"
        val noPasswordError = SignInErrorModel()
        val passwordError = SignInErrorModel(passwordError = true, passwordHasCapitalizedLetterError = true)

        signIn(store, email, password)

        TestCase.assertNotSame(passwordError, store.state.signInError)

        store.accept(SignInStore.Intent.RefreshPasswordError)

        TestCase.assertEquals(noPasswordError, store.state.signInError)
    }

    @Test
    fun checkOnForgetPasswordClickEvent() = runTest {
        val store = store()

        turbineScope {
            store.labels.test {
                store.accept(SignInStore.Intent.ForgetPasswordClick)

                TestCase.assertEquals(
                    SignInStore.Label.ForgetPasswordClick,
                    awaitItem()
                )
            }
        }
    }

    @Test
    fun checkSignUpClickEvent() = runTest {
        val store = store()

        turbineScope {
            store.labels.test {
                store.accept(SignInStore.Intent.SignUp)

                TestCase.assertEquals(
                    SignInStore.Label.SignUpClicked,
                    awaitItem()
                )
            }
        }
    }

    @Test
    fun checkSignInWrongEmailEvent() = runTest {
        val store = store()

        val wrongEmail = "email"
        val password = "Password"
        val emailError = SignInErrorModel(emailError = true)

        store.accept(SignInStore.Intent.SignIn(wrongEmail, password))

        coVerify(exactly = 0) { signInUseCase(wrongEmail, password) }

        TestCase.assertEquals(emailError, store.state.signInError)
    }

    @Test
    fun checkSignInWrongPasswordWrongLengthEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val wrongPassword = "L"
        val passwordError = SignInErrorModel(passwordError = true, passwordLengthError = true)

        store.accept(SignInStore.Intent.SignIn(email, wrongPassword))

        coVerify(exactly = 0) { signInUseCase(email, wrongPassword) }

        TestCase.assertEquals(passwordError, store.state.signInError)
    }

    @Test
    fun checkSignInWrongPasswordNoCapitalizedLetterEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val wrongPassword = "password"
        val passwordError = SignInErrorModel(passwordError = true, passwordHasCapitalizedLetterError = true)

        store.accept(SignInStore.Intent.SignIn(email, wrongPassword))

        coVerify(exactly = 0) { signInUseCase(email, wrongPassword) }

        TestCase.assertEquals(passwordError, store.state.signInError)
    }

    @Test
    fun checkSignInWrongPasswordEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val wrongPassword = "g"
        val passwordError = SignInErrorModel(
            passwordError = true,
            passwordHasCapitalizedLetterError = true,
            passwordLengthError = true
        )

        store.accept(SignInStore.Intent.SignIn(email, wrongPassword))

        coVerify(exactly = 0) { signInUseCase(email, wrongPassword) }

        TestCase.assertEquals(passwordError, store.state.signInError)
    }

    @Test
    fun checkSignInServerErrorEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val wrongPassword = "Password"
        val passwordError = SignInErrorModel(networkError = true)

        coEvery { signInUseCase(email, wrongPassword) } returns false
        store.accept(SignInStore.Intent.SignIn(email, wrongPassword))

        coVerify { signInUseCase(email, wrongPassword) }
        TestCase.assertEquals(passwordError, store.state.signInError)
    }

    @Test
    fun checkSignInEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val wrongPassword = "Password"

        coEvery { signInUseCase(email, wrongPassword) } returns true

        turbineScope {
            store.labels.test {
                store.accept(SignInStore.Intent.SignIn(email, wrongPassword))
                coVerify { signInUseCase(email, wrongPassword) }

                TestCase.assertEquals(
                    SignInStore.Label.LogIn,
                    awaitItem()
                )
            }
        }
    }

    private fun signIn(store: SignInStore, email: String, password: String) {
        store.accept(SignInStore.Intent.SignIn(email, password))
    }
}