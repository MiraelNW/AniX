package com.miraeldev.impl.repository

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.miraeldev.api.AppNetworkClient
import com.miraeldev.api.FavouriteAnimeDao
import com.miraeldev.api.UserDao
import com.miraeldev.api.VideoPlayerDataRepository
import com.miraeldev.impl.repository.utils.getDefaultHttpResponse
import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto
import com.miraeldev.models.dto.toAnimeDetailInfo
import com.miraeldev.models.result.FailureCauses
import com.miraeldev.models.result.ResultAnimeDetail
import com.miraeldev.models.user.User
import io.ktor.client.call.body
import io.ktor.util.InternalAPI
import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(InternalAPI::class)
class AnimeDetailDataRepositoryImplTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var appNetworkClient: AppNetworkClient
    @MockK
    lateinit var favouriteAnimeDao: FavouriteAnimeDao
    @MockK
    lateinit var userDao: UserDao
    @MockK
    lateinit var videoPlayerDataRepository: VideoPlayerDataRepository

    private lateinit var animeDetailDataRepositoryImpl: AnimeDetailDataRepositoryImpl

    @RelaxedMockK
    lateinit var animeInfoDto: AnimeInfoDto
    @RelaxedMockK
    lateinit var animeInfo: AnimeInfo
    @RelaxedMockK
    lateinit var user: User

    @Before
    fun beforeTest() {
        animeDetailDataRepositoryImpl = AnimeDetailDataRepositoryImpl(
            videoPlayerDataRepository,
            favouriteAnimeDao,
            appNetworkClient,
            userDao
        )
    }

    @Test
    fun loadAnimeDetailBadResponseTest() = runTest {
        coEvery { appNetworkClient.searchAnimeById(any(), any()) } returns getDefaultHttpResponse(400)
        coEvery { userDao.getUser() } returns user

        animeDetailDataRepositoryImpl.loadAnimeDetail(-1)

        coVerify(exactly = 0) { videoPlayerDataRepository.loadVideoPlayer(any()) }

        turbineScope {
            animeDetailDataRepositoryImpl.getAnimeDetail().test {
                animeDetailDataRepositoryImpl.loadAnimeDetail(-1)
                assertEquals(ResultAnimeDetail.Failure(FailureCauses.BadServer), awaitItem())
            }
        }
    }

    @Test
    fun loadAnimeDetailSuccessResponseTest() = runTest {
        coEvery { appNetworkClient.searchAnimeById(any(), any()) } returns getDefaultHttpResponse(200)
        coEvery { videoPlayerDataRepository.loadVideoPlayer(any()) } returns Unit
        coEvery { userDao.getUser() } returns user
        coEvery { getDefaultHttpResponse(200).body<AnimeInfoDto>() } returns animeInfoDto

        animeDetailDataRepositoryImpl.loadAnimeDetail(-1)

        coVerify { videoPlayerDataRepository.loadVideoPlayer(any()) }

        turbineScope {
            animeDetailDataRepositoryImpl.getAnimeDetail().test {
                animeDetailDataRepositoryImpl.loadAnimeDetail(-1)
                assertEquals(ResultAnimeDetail.Success(animeList = listOf(animeInfoDto.toAnimeDetailInfo())), awaitItem())
            }
        }
    }

    @Test
    fun loadAnimeDetailUserNullTest() = runTest {
        coEvery { userDao.getUser() } returns null

        animeDetailDataRepositoryImpl.loadAnimeDetail(-1)

        coVerify { appNetworkClient.searchAnimeById(any(), any()) wasNot called }
    }

    @Test
    fun selectAnimeItemSuccessResponseTest() = runTest {
        coEvery { appNetworkClient.selectAnimeItem(any(), any()) } returns getDefaultHttpResponse(200)
        coEvery { favouriteAnimeDao.insertFavouriteAnimeItem(any()) } returns Unit

        animeDetailDataRepositoryImpl.selectAnimeItem(true, animeInfo)

        coVerify() { favouriteAnimeDao.insertFavouriteAnimeItem(any()) }
        coVerify(exactly = 0) { favouriteAnimeDao.deleteFavouriteAnimeItem(any()) }
    }

    @Test
    fun unSelectAnimeItemSuccessResponseTest() = runTest {
        coEvery { appNetworkClient.selectAnimeItem(any(), any()) } returns getDefaultHttpResponse(200)
        coEvery { favouriteAnimeDao.deleteFavouriteAnimeItem(any()) } returns Unit

        animeDetailDataRepositoryImpl.selectAnimeItem(false, animeInfo)

        coVerify(exactly = 0) { favouriteAnimeDao.insertFavouriteAnimeItem(any()) }
        coVerify() { favouriteAnimeDao.deleteFavouriteAnimeItem(any()) }
    }

    @Test
    fun selectAnimeItemFailureResponseTest() = runTest {
        coEvery { appNetworkClient.selectAnimeItem(any(), any()) } returns getDefaultHttpResponse(400)

        animeDetailDataRepositoryImpl.selectAnimeItem(true, animeInfo)

        coVerify(exactly = 0) { favouriteAnimeDao.insertFavouriteAnimeItem(any()) }
        coVerify(exactly = 0) { favouriteAnimeDao.deleteFavouriteAnimeItem(any()) }
    }
}