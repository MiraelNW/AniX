package com.miraeldev.impl.repository

import com.miraeldev.api.AuthNetworkClient
import com.miraeldev.api.PreferenceClient
import com.miraeldev.api.UserAuthRepository
import com.miraeldev.api.UserDao
import com.miraeldev.api.UserDataRepository
import com.miraeldev.impl.models.auth.Token
import com.miraeldev.impl.repository.utils.getDefaultHttpResponse
import com.miraeldev.models.user.User
import io.ktor.client.call.body
import io.ktor.util.InternalAPI
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(InternalAPI::class)
class UserAuthDataRepositoryImplTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var authNetworkClient: AuthNetworkClient

    @MockK
    lateinit var preferenceClient: PreferenceClient

    @MockK
    lateinit var userAuthRepository: UserAuthRepository

    @MockK
    lateinit var userDataRepository: UserDataRepository

    @MockK
    lateinit var userDao: UserDao

    private lateinit var userAuthDataRepositoryImpl: UserAuthDataRepositoryImpl

    private val token = Token("bearer", "refresh")
    private val user = User()

    private fun initRepository() {
        userAuthDataRepositoryImpl = UserAuthDataRepositoryImpl(
                preferenceClient = preferenceClient,
                userAuthRepository = userAuthRepository,
                userDataRepository = userDataRepository,
                userDao = userDao,
                authNetworkClient = authNetworkClient
        )
    }

    @Test
    fun signUpSuccessTest() = runTest {
        coEvery { authNetworkClient.signUp(user) } returns getDefaultHttpResponse(200)
        initRepository()

        val signUp = userAuthDataRepositoryImpl.signUp(user)

        TestCase.assertEquals(true, signUp)
    }

    @Test
    fun signUpBadResponseTest() = runTest {
        coEvery { authNetworkClient.signUp(user) } returns getDefaultHttpResponse(400)
        initRepository()

        val signUp = userAuthDataRepositoryImpl.signUp(user)

        TestCase.assertEquals(false, signUp)
    }

    @Test
    fun signInSuccessTest() = runTest {
        coEvery { authNetworkClient.signIn("email", "password") } returns getDefaultHttpResponse(200)
        coEvery { preferenceClient.saveBearerToken(token.bearerToken) } returns Unit
        coEvery { preferenceClient.saveRefreshToken(token.refreshToken) } returns Unit
        coEvery { userDataRepository.saveRemoteUser() } returns true
        coEvery { userAuthRepository.setUserAuthorizedStatus() } returns Unit
        coEvery { getDefaultHttpResponse(200).body<Token>() } returns token
        initRepository()

        val signIn = userAuthDataRepositoryImpl.signIn("email", "password")

        coVerify { userAuthRepository.setUserAuthorizedStatus() }
        TestCase.assertEquals(true, signIn)
    }

    @Test
    fun signInNotSavingRemoteUserTest() = runTest {
        coEvery { authNetworkClient.signIn("email", "password") } returns getDefaultHttpResponse(200)
        coEvery { preferenceClient.saveBearerToken(token.bearerToken) } returns Unit
        coEvery { preferenceClient.saveRefreshToken(token.refreshToken) } returns Unit
        coEvery { userDataRepository.saveRemoteUser() } returns false
        coEvery { userAuthRepository.setUserAuthorizedStatus() } returns Unit
        coEvery { getDefaultHttpResponse(200).body<Token>() } returns token
        initRepository()

        val signIn = userAuthDataRepositoryImpl.signIn("email", "password")

        coVerify(exactly = 0) { userAuthRepository.setUserAuthorizedStatus() }
        TestCase.assertEquals(true, signIn)
    }

    @Test
    fun signInBadResponseTest() = runTest {
        coEvery { authNetworkClient.signIn("email", "password") } returns getDefaultHttpResponse(400)
        initRepository()

        val signIn = userAuthDataRepositoryImpl.signIn("email", "password")

        TestCase.assertEquals(false, signIn)
    }

    @Test
    fun logOutUserSuccessTest() = runTest {
        coEvery { preferenceClient.getRefreshToken() } returns "refresh"
        coEvery { authNetworkClient.logOutUser("refresh") } returns getDefaultHttpResponse(200)
        coEvery { userAuthRepository.setUserAuthorizedStatus() } returns Unit
        coEvery { getDefaultHttpResponse(200).body<Token>() } returns token
        initRepository()

        userAuthDataRepositoryImpl.logOutUser()

        coVerify { userAuthRepository.setUserUnAuthorizedStatus() }
    }

    @Test
    fun logOutUserBadResponseTest() = runTest {
        coEvery { preferenceClient.getRefreshToken() } returns "refresh"
        coEvery { authNetworkClient.logOutUser("refresh") } returns getDefaultHttpResponse(400)
        initRepository()

        userAuthDataRepositoryImpl.logOutUser()

        coVerify(exactly = 0) { userAuthRepository.setUserUnAuthorizedStatus() }
    }

    @Test
    fun checkAuthStateAuthorizedTest() = runTest {
        coEvery { preferenceClient.getRefreshToken() } returns "refresh"
        coEvery { preferenceClient.getBearerToken() } returns "bearer"
        coEvery { userAuthRepository.setUserAuthorizedStatus() } returns Unit
        initRepository()

        userAuthDataRepositoryImpl.checkAuthState()

        coVerify { userAuthRepository.setUserAuthorizedStatus() }
    }

    @Test
    fun checkAuthStateNotAuthorizedTest() = runTest {
        coEvery { preferenceClient.getRefreshToken() } returns "refresh"
        coEvery { preferenceClient.getBearerToken() } returns ""
        coEvery { userAuthRepository.setUserUnAuthorizedStatus() } returns Unit
        initRepository()

        userAuthDataRepositoryImpl.checkAuthState()

        coVerify { userAuthRepository.setUserUnAuthorizedStatus() }
    }

    @Test
    fun verifyOtpCodeSuccessSignInBadRequestTest() = runTest {
        coEvery { authNetworkClient.verifyOtpCode(user, "123") } returns getDefaultHttpResponse(200)
        coEvery { authNetworkClient.signIn(user.email, user.password) } returns getDefaultHttpResponse(400)
        coEvery { userAuthRepository.setUserAuthorizedStatus() } returns Unit
        initRepository()

        userAuthDataRepositoryImpl.verifyOtpCode(user, "123")

        coVerify(exactly = 0) { userAuthRepository.setUserAuthorizedStatus() }
    }

    @Test
    fun verifyOtpCodeSuccessTest() = runTest {
        coEvery { authNetworkClient.verifyOtpCode(user, "123") } returns getDefaultHttpResponse(200)
        coEvery { authNetworkClient.signIn(user.email, user.password) } returns getDefaultHttpResponse(200)
        coEvery { userAuthRepository.setUserAuthorizedStatus() } returns Unit
        coEvery { preferenceClient.saveBearerToken(token.bearerToken) } returns Unit
        coEvery { preferenceClient.saveRefreshToken(token.refreshToken) } returns Unit
        coEvery { userDataRepository.saveRemoteUser() } returns true
        coEvery { getDefaultHttpResponse(200).body<Token>() } returns token
        initRepository()

        userAuthDataRepositoryImpl.verifyOtpCode(user, "123")

        coVerify { userAuthRepository.setUserAuthorizedStatus() }
    }

    @Test
    fun verifyOtpCodeBadResponseTest() = runTest {
        coEvery { authNetworkClient.verifyOtpCode(user, "123") } returns getDefaultHttpResponse(400)
        initRepository()

        userAuthDataRepositoryImpl.verifyOtpCode(user, "123")

        coVerify(exactly = 0) { authNetworkClient.signIn("email", "password") }
    }
}