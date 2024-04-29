package com.miraeldev.impl.repository

import com.miraeldev.api.AuthNetworkClient
import com.miraeldev.api.PreferenceClient
import com.miraeldev.api.UserAuthRepository
import com.miraeldev.impl.models.auth.Token
import com.miraeldev.impl.repository.utils.getDefaultHttpResponse
import io.ktor.client.call.body
import io.ktor.util.InternalAPI
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(InternalAPI::class)
class ForgotPasswordDataRepositoryImplTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var authNetworkClient: AuthNetworkClient

    @MockK
    lateinit var preferenceClient: PreferenceClient

    @MockK
    lateinit var userAuthRepository: UserAuthRepository


    private lateinit var forgotPasswordDataRepositoryImpl: ForgotPasswordDataRepositoryImpl

    private fun initRepository() {
        forgotPasswordDataRepositoryImpl = ForgotPasswordDataRepositoryImpl(
                authNetworkClient,
                preferenceClient,
                userAuthRepository
        )
    }

    @Test
    fun saveNewPasswordSuccessTest() = runTest {
        val token = Token("bearer", "refresh")
        coEvery { authNetworkClient.saveNewPassword("email", "newPassword") } returns getDefaultHttpResponse(200)
        coEvery { getDefaultHttpResponse(200).body<Token>() } returns token
        initRepository()

        forgotPasswordDataRepositoryImpl.saveNewPassword("email", "newPassword")

        coVerify { preferenceClient.saveBearerToken(token.bearerToken) }
    }

    @Test
    fun saveNewPasswordBadResponseTest() = runTest {
        val token = Token("bearer", "refresh")
        coEvery { authNetworkClient.saveNewPassword("email", "newPassword") } returns getDefaultHttpResponse(400)
        initRepository()

        val save = forgotPasswordDataRepositoryImpl.saveNewPassword("email", "newPassword")

        coVerify(exactly = 0) { preferenceClient.saveBearerToken(token.bearerToken) }
        TestCase.assertEquals(false, save)
    }

    @Test
    fun verifyOtpSuccessTest() = runTest {
        coEvery { authNetworkClient.verifyOtpForgotPassword("123") } returns getDefaultHttpResponse(200)
        initRepository()

        val verified = forgotPasswordDataRepositoryImpl.verifyOtp("123")

        TestCase.assertEquals(true, verified)
    }

    @Test
    fun verifyOtpBadResponseTest() = runTest {
        coEvery { authNetworkClient.verifyOtpForgotPassword("123") } returns getDefaultHttpResponse(400)
        initRepository()

        val verified = forgotPasswordDataRepositoryImpl.verifyOtp("123")

        TestCase.assertEquals(false, verified)
    }

    @Test
    fun checkEmailExistSuccessTest() = runTest {
        coEvery { authNetworkClient.verifyOtpForgotPassword("123") } returns getDefaultHttpResponse(200)
        initRepository()

        val verified = forgotPasswordDataRepositoryImpl.verifyOtp("123")

        TestCase.assertEquals(true, verified)
    }

    @Test
    fun checkEmailExistBadResponseTest() = runTest {
        coEvery { authNetworkClient.checkEmailExist("email") } returns getDefaultHttpResponse(400)
        initRepository()

        val verified = forgotPasswordDataRepositoryImpl.checkEmailExist("email")

        TestCase.assertEquals(false, verified)
    }
}