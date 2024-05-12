package com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.store

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.miraeldev.signup.domain.useCases.UpdateUserUseCase
import com.miraeldev.signup.domain.useCases.VerifyOtpCodeUseCase
import com.miraeldev.signup.presentation.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CodeVerifyStoreFactoryTest {

    @get:Rule
    val rule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val storeFactory = DefaultStoreFactory()

    private val updateUserUseCase: UpdateUserUseCase = mockk(relaxed = true)
    private val verifyOtpCodeUseCase: VerifyOtpCodeUseCase = mockk(relaxed = true)

    private fun store(): CodeVerifyStore = CodeVerifyStoreFactory(
        storeFactory,
        updateUserUseCase,
        verifyOtpCodeUseCase
    ).create()

    @Test
    fun checkVerifyOtpError() {
        coEvery { verifyOtpCodeUseCase(any(), any()) } returns false
        val error = true
        val store = store()

        store.accept(CodeVerifyStore.Intent.VerifyOtp("otp", "email", "password"))

        TestCase.assertEquals(error, store.state.verifyOtpError)
    }

    @Test
    fun checkVerifyOtpSuccess() {
        coEvery { verifyOtpCodeUseCase(any(), any()) } returns true
        val error = false
        val store = store()

        store.accept(CodeVerifyStore.Intent.VerifyOtp("otp", "email", "password"))

        TestCase.assertEquals(error, store.state.verifyOtpError)
    }

    @Test
    fun checkOnOtpChanged() {
        val otp = "otp"
        val store = store()

        store.accept(CodeVerifyStore.Intent.OnOtpChange(otp))

        TestCase.assertEquals(otp, store.state.otpText)
    }

    @Test
    fun checkUpdateUser() {
        val email = "email"
        val store = store()

        store.accept(CodeVerifyStore.Intent.UpdateUser(email))

        coVerify { updateUserUseCase(email) }
    }

    @Test
    fun checkOnBackClickEvent() = runTest {
        val store = store()

        turbineScope {
            store.labels.test {
                store.accept(CodeVerifyStore.Intent.OnBackClicked)

                TestCase.assertEquals(
                    CodeVerifyStore.Label.OnBackClicked,
                    awaitItem()
                )
            }
        }
    }
}