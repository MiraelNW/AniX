package com.miraeldev.forgotpassword.presentation.codeVerifyResetPasswordScreen.codeVerifyResetPasswordComponent.store

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.miraeldev.MainDispatcherRule
import com.miraeldev.forgotpassword.domain.usecases.VerifyOtpUseCase
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CodeVerifyStoreRPFactoryTest {

    @get:Rule
    val rule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val storeFactory = DefaultStoreFactory()

    private val verifyOtpCodeUseCase: VerifyOtpUseCase = mockk(relaxed = true)

    private fun store(): CodeVerifyRPStore = CodeVerifyStoreRPFactory(
        storeFactory,
        verifyOtpCodeUseCase
    ).create()

    @Test
    fun checkVerifyOtpError() {
        coEvery { verifyOtpCodeUseCase(any()) } returns false
        val error = true
        val store = store()

        store.accept(CodeVerifyRPStore.Intent.VerifyOtp("otp"))

        TestCase.assertEquals(error, store.state.verifyOtpError)
    }

    @Test
    fun checkVerifyOtpSuccess() {
        coEvery { verifyOtpCodeUseCase(any()) } returns true
        val error = false
        val store = store()

        store.accept(CodeVerifyRPStore.Intent.VerifyOtp("otp"))

        TestCase.assertEquals(error, store.state.verifyOtpError)
    }

    @Test
    fun checkOnOtpChanged() {
        val otp = "otp"
        val store = store()

        store.accept(CodeVerifyRPStore.Intent.OnOtpChange(otp))

        TestCase.assertEquals(otp, store.state.otpText)
    }

    @Test
    fun checkOnBackClickEvent() = runTest {
        val store = store()

        turbineScope {
            store.labels.test {
                store.accept(CodeVerifyRPStore.Intent.OnBackClicked)

                TestCase.assertEquals(
                    CodeVerifyRPStore.Label.OnBackClicked,
                    awaitItem()
                )
            }
        }
    }
}