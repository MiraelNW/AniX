package com.miraeldev.signup.presentation.signUpScreen.signUpComponent.store

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.miraeldev.models.user.User
import com.miraeldev.signin.domain.model.SignUpErrorModel
import com.miraeldev.signup.domain.useCases.SignUpUseCase
import com.miraeldev.signup.presentation.MainDispatcherRule
import com.miraeldev.utils.ValidatePassword
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class SignUpStoreFactoryTest {
    @get:Rule
    val rule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val storeFactory = DefaultStoreFactory()

    private val signUpUseCase: SignUpUseCase = mockk(relaxed = true)
    private val validatePassword: ValidatePassword = ValidatePassword()

    private fun store(): SignUpStore = SignUpStoreFactory(
        storeFactory,
        signUpUseCase,
        validatePassword
    ).create()

    @Test
    fun checkChangeEmailEvent() = runTest {
        val email = "email"
        val store = store()

        store.accept(SignUpStore.Intent.ChangeEmail(email))

        TestCase.assertEquals(email, store.state.email)
    }

    @Test
    fun checkChangePasswordEvent() = runTest {
        val password = "password"
        val store = store()

        store.accept(SignUpStore.Intent.ChangePassword(password))

        TestCase.assertEquals(password, store.state.password)
    }

    @Test
    fun checkChangeImageEvent() = runTest {
        val image = "image"
        val store = store()

        store.accept(SignUpStore.Intent.ChangeImage(image))

        TestCase.assertEquals(image, store.state.image)
    }

    @Test
    fun checkChangeUsernameEvent() = runTest {
        val username = "username"
        val store = store()

        store.accept(SignUpStore.Intent.ChangeUsername(username))

        TestCase.assertEquals(username, store.state.username)
    }

    @Test
    fun checkChangeRepeatedPasswordEvent() = runTest {
        val repeatedPassword = "repeatedPassword"
        val store = store()

        store.accept(SignUpStore.Intent.ChangeRepeatedPassword(repeatedPassword))

        TestCase.assertEquals(repeatedPassword, store.state.repeatedPassword)
    }

    @Test
    fun checkRefreshEmailErrorEvent() = runTest {
        val store = store()
        val email = "email"
        val password = "Password"
        val repeatedPassword = "Password"
        val emailError = SignUpErrorModel(emailError = true)
        val noEmailError = SignUpErrorModel()

        invokeSignUp(store, email, password, repeatedPassword)

        TestCase.assertEquals(emailError, store.state.signUpError)

        store.accept(SignUpStore.Intent.RefreshEmailError)

        TestCase.assertEquals(noEmailError, store.state.signUpError)
    }

    @Test
    fun checkRefreshPasswordErrorEvent() = runTest {
        val store = store()
        val email = "email@mail.ru"
        val password = "password"
        val repeatedPassword = "password"
        val passwordError = SignUpErrorModel(passwordError = true, passwordHasCapitalizedLetterError = true)
        val noPasswordError = SignUpErrorModel()

        invokeSignUp(store, email, password, repeatedPassword)

        TestCase.assertEquals(passwordError, store.state.signUpError)

        store.accept(SignUpStore.Intent.RefreshPasswordError)

        TestCase.assertEquals(noPasswordError, store.state.signUpError)
    }

    @Test
    fun checkRefreshRepeatedPasswordErrorEvent() = runTest {
        val store = store()
        val email = "email@mail.ru"
        val password = "Password"
        val repeatedPassword = "password"
        val passwordError = SignUpErrorModel(repeatedPasswordError = false)

        invokeSignUp(store, email, password, repeatedPassword)

        store.accept(SignUpStore.Intent.RefreshRepeatedPasswordError)

        TestCase.assertEquals(passwordError, store.state.signUpError)
    }

    @Test
    fun checkOnForgetPasswordClickEvent() = runTest {
        val store = store()

        turbineScope {
            store.labels.test {
                store.accept(SignUpStore.Intent.OnBackClick)

                TestCase.assertEquals(
                    SignUpStore.Label.OnBackClicked,
                    awaitItem()
                )
            }
        }
    }

    @Test
    fun checkSignUpWrongEmailEvent() = runTest {
        val store = store()

        val wrongEmail = "email"
        val password = "Password"
        val repeatedPassword = "Password"
        val user = createUser(wrongEmail, password)
        val emailError = SignUpErrorModel(emailError = true)

        store.accept(SignUpStore.Intent.OnSignUpClick("", "", wrongEmail, password, repeatedPassword))

        coVerify(exactly = 0) { signUpUseCase(user) }

        TestCase.assertEquals(emailError, store.state.signUpError)
    }

    @Test
    fun checkSignUpWrongRepeatedPasswordEvent() = runTest {
        val store = store()

        val wrongEmail = "email@mail.ru"
        val password = "Password"
        val repeatedPassword = "password"
        val user = createUser(wrongEmail, password)
        val repeatedPasswordError = SignUpErrorModel(repeatedPasswordError = true)

        store.accept(SignUpStore.Intent.OnSignUpClick("", "", wrongEmail, password, repeatedPassword))

        coVerify(exactly = 0) { signUpUseCase(user) }

        TestCase.assertEquals(repeatedPasswordError, store.state.signUpError)
    }

    @Test
    fun checkSignUpWrongPasswordWrongLengthEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val wrongPassword = "L"
        val repeatedPassword = "L"
        val user = createUser(email, wrongPassword)
        val passwordError = SignUpErrorModel(passwordError = true, passwordLengthError = true)

        store.accept(SignUpStore.Intent.OnSignUpClick("", "", email, wrongPassword, repeatedPassword))

        coVerify(exactly = 0) { signUpUseCase(user) }

        TestCase.assertEquals(passwordError, store.state.signUpError)
    }

    @Test
    fun checkSignUpWrongPasswordNoCapitalizedLetterEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val wrongPassword = "password"
        val repeatedPassword = "password"
        val user = createUser(email, wrongPassword)
        val passwordError = SignUpErrorModel(passwordError = true, passwordHasCapitalizedLetterError = true)

        store.accept(SignUpStore.Intent.OnSignUpClick("", "", email, wrongPassword, repeatedPassword))

        coVerify(exactly = 0) { signUpUseCase(user) }

        TestCase.assertEquals(passwordError, store.state.signUpError)
    }

    @Test
    fun checkSignUpWrongPasswordEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val wrongPassword = "g"
        val repeatedPassword = "g"
        val user = createUser(email, wrongPassword)
        val passwordError = SignUpErrorModel(
            passwordError = true,
            passwordHasCapitalizedLetterError = true,
            passwordLengthError = true
        )

        store.accept(SignUpStore.Intent.OnSignUpClick("", "", email, wrongPassword, repeatedPassword))

        coVerify(exactly = 0) { signUpUseCase(user) }

        TestCase.assertEquals(passwordError, store.state.signUpError)
    }

    @Test
    fun checkSignUpServerErrorEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val password = "Password"
        val repeatedPassword = "Password"
        val user = createUser(email, password)
        val passwordError = SignUpErrorModel(networkError = true)

        coEvery { signUpUseCase(user) } returns false
        store.accept(SignUpStore.Intent.OnSignUpClick("", "", email, password, repeatedPassword))

        coVerify { signUpUseCase(user) }
        TestCase.assertEquals(passwordError, store.state.signUpError)
    }

    @Test
    fun checkSignUpEvent() = runTest {
        val store = store()

        val email = "email@mail.ru"
        val password = "Password"
        val repeatedPassword = "Password"
        val user = createUser(email, password)

        coEvery { signUpUseCase(user) } returns true

        turbineScope {
            store.labels.test {
                store.accept(SignUpStore.Intent.OnSignUpClick("", "", email, password, repeatedPassword))
                coVerify { signUpUseCase(user) }

                TestCase.assertEquals(
                    SignUpStore.Label.OnSignUpClicked(email, password),
                    awaitItem()
                )
            }
        }
    }

    private fun createUser(email: String, password: String, image: String = "", username: String = "") = User(
        email = email,
        password = password,
        image = image,
        username = username
    )

    private fun invokeSignUp(
        store: SignUpStore,
        email: String,
        password: String,
        repeatedPassword: String,
        image: String = "",
        username: String = ""
    ) {
        store.accept(
            SignUpStore.Intent.OnSignUpClick(
                email = email,
                password = password,
                repeatedPassword = repeatedPassword,
                image = image,
                username = username
            )
        )
    }
}