package com.miraeldev.impl.repository

import com.miraeldev.api.AppNetworkClient
import com.miraeldev.api.LocalUserStoreApi
import com.miraeldev.api.UserDao
import com.miraeldev.impl.mapper.UserModelsMapper
import com.miraeldev.impl.repository.utils.getDefaultHttpResponse
import com.miraeldev.models.anime.LastWatchedAnime
import com.miraeldev.models.dto.UserDto
import com.miraeldev.models.dto.toModel
import com.miraeldev.models.user.User
import io.ktor.client.call.body
import io.ktor.util.InternalAPI
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(InternalAPI::class)
class UserDataRepositoryImplTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var userDao: UserDao

    @MockK
    lateinit var localUserManager: LocalUserStoreApi

    @MockK
    lateinit var userModelsMapper: UserModelsMapper

    @MockK
    lateinit var appNetworkClient: AppNetworkClient

    private lateinit var userDataRepositoryImpl: UserDataRepositoryImpl

    private val userDto = UserDto(1, "", "", "", "")
    private val lastWatchedAnime = LastWatchedAnime(123)

    private fun initRepository() {
        userDataRepositoryImpl = UserDataRepositoryImpl(
            appNetworkClient = appNetworkClient,
            userDao = userDao,
            localUserManager = localUserManager,
            userModelsMapper = userModelsMapper
        )
    }

    @Test
    fun saveRemoteUserSuccessTest() = runTest {
        coEvery { appNetworkClient.saveRemoteUser() } returns getDefaultHttpResponse(200)
        coEvery { getDefaultHttpResponse(200).body<UserDto>() } returns userDto
        coEvery { userDao.insertUser(any()) } returns Unit
        initRepository()

        val saveRemoteUser = userDataRepositoryImpl.saveRemoteUser()

        coVerify { userDao.insertUser(any()) }
        TestCase.assertEquals(true, saveRemoteUser)
    }

    @Test
    fun saveRemoteUserBadRequestTest() = runTest {
        coEvery { appNetworkClient.saveRemoteUser() } returns getDefaultHttpResponse(400)
        initRepository()

        val saveRemoteUser = userDataRepositoryImpl.saveRemoteUser()

        coVerify(exactly = 0) { userDao.insertUser(any()) }
        TestCase.assertEquals(false, saveRemoteUser)
    }

    @Test
    fun saveLastWatchedAnimeSuccessTest() = runTest {
        coEvery { userDao.getUserFlow() } returns flowOf(User(lastWatchedAnime = lastWatchedAnime))
        coEvery { userDao.insertUser(any()) } returns Unit
        initRepository()

        userDataRepositoryImpl.saveLastWatchedAnime(lastWatchedAnime)

        coVerify { userDao.insertUser(any()) }
    }

    @Test
    fun saveLastWatchedAnimeBadRequestTest() = runTest {
        coEvery { appNetworkClient.saveRemoteUser() } returns getDefaultHttpResponse(400)
        initRepository()

        val saveRemoteUser = userDataRepositoryImpl.saveRemoteUser()

        coVerify(exactly = 0) { userDao.insertUser(userDto.toModel()) }
        TestCase.assertEquals(false, saveRemoteUser)
    }
}