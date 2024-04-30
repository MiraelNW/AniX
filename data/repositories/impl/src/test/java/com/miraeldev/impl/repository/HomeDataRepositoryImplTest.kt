package com.miraeldev.impl.repository

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.miraeldev.api.AppNetworkClient
import com.miraeldev.api.UserDataRepository
import com.miraeldev.api.filmCategory.FilmCategoryDao
import com.miraeldev.api.nameCategory.NameCategoryDao
import com.miraeldev.api.newCategory.NewCategoryDao
import com.miraeldev.api.popularCategory.PopularCategoryDao
import com.miraeldev.impl.repository.utils.getDefaultHttpResponse
import com.miraeldev.models.dto.AnimeInfoDto
import com.miraeldev.models.dto.Response
import com.miraeldev.models.dto.toAnimeDetailInfo
import com.miraeldev.models.dto.toLastWatched
import com.miraeldev.models.user.User
import io.ktor.client.call.body
import io.ktor.util.InternalAPI
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(InternalAPI::class)
class HomeDataRepositoryImplTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var appNetworkClient: AppNetworkClient

    @MockK
    lateinit var userDataRepository: UserDataRepository

    @MockK
    lateinit var newCategoryDao: NewCategoryDao

    @MockK
    lateinit var popularCategoryDao: PopularCategoryDao

    @MockK
    lateinit var nameCategoryDao: NameCategoryDao

    @MockK
    lateinit var filmCategoryDao: FilmCategoryDao

    private lateinit var homeDataRepositoryImpl: HomeDataRepositoryImpl

    @RelaxedMockK
    lateinit var animeInfoDto: AnimeInfoDto

    private fun initRepository() {
        homeDataRepositoryImpl = HomeDataRepositoryImpl(
            appNetworkClient = appNetworkClient,
            userDataRepository = userDataRepository,
            newCategoryDao = newCategoryDao,
            popularCategoryDao = popularCategoryDao,
            nameCategoryDao = nameCategoryDao,
            filmCategoryDao = filmCategoryDao,
            systemCurrentTime = 0L
        )
    }

    @Test
    fun loadNewAnimeListTest() = runTest {
        initRepository()
        coEvery { appNetworkClient.getNewCategoryList(0).body<Response>() } returns
            Response(false, listOf())
        coEvery { appNetworkClient.getPopularCategoryList(0).body<Response>() } returns
            Response(false, listOf())
        coEvery { appNetworkClient.getNameCategoryList(0).body<Response>() } returns
            Response(false, listOf())
        coEvery { appNetworkClient.getFilmCategoryList(0).body<Response>() } returns
            Response(false, listOf())
        coEvery { homeDataRepositoryImpl.loadData() } returns mapOf()
        coEvery { newCategoryDao.isEmpty() } returns true
        coEvery { popularCategoryDao.isEmpty() } returns false
        coEvery { filmCategoryDao.isEmpty() } returns false
        coEvery { nameCategoryDao.isEmpty() } returns false
        coEvery { newCategoryDao.getCreateTime() } returns 0L
        coEvery { newCategoryDao.insertAll(any()) } returns Unit
        coEvery { popularCategoryDao.insertAll(any()) } returns Unit
        coEvery { nameCategoryDao.insertAll(any()) } returns Unit
        coEvery { filmCategoryDao.insertAll(any()) } returns Unit

        homeDataRepositoryImpl.loadData()

        coVerify { newCategoryDao.insertAll(any()) }
        coVerify(exactly = 0) { newCategoryDao.getAnimeList() }
    }

    @Test
    fun loadDataTest() = runTest {
        initRepository()
        coEvery { homeDataRepositoryImpl.loadData() } returns mapOf()
        coEvery { newCategoryDao.getCreateTime() } returns 0L
        coEvery { newCategoryDao.isEmpty() } returns false
        coEvery { popularCategoryDao.isEmpty() } returns false
        coEvery { filmCategoryDao.isEmpty() } returns false
        coEvery { nameCategoryDao.isEmpty() } returns false
        coEvery { newCategoryDao.insertAll(any()) } returns Unit
        coEvery { popularCategoryDao.insertAll(any()) } returns Unit
        coEvery { nameCategoryDao.insertAll(any()) } returns Unit
        coEvery { filmCategoryDao.insertAll(any()) } returns Unit
        coEvery { newCategoryDao.getAnimeList() } returns listOf()
        coEvery { popularCategoryDao.getAnimeList() } returns listOf()
        coEvery { nameCategoryDao.getAnimeList() } returns listOf()
        coEvery { filmCategoryDao.getAnimeList() } returns listOf()

        homeDataRepositoryImpl.loadData()

        coVerify { newCategoryDao.getAnimeList() }
        coVerify(exactly = 0) { newCategoryDao.insertAll(any()) }
    }

    @Test
    fun getUserInfoSuccessTest() = runTest {
        initRepository()
        coEvery { userDataRepository.getUserInfo() } returns User(lastWatchedAnime = null)
        coEvery { appNetworkClient.searchAnimeById(-1, any()) } returns getDefaultHttpResponse(200)
        coEvery { getDefaultHttpResponse(200).body<AnimeInfoDto>() } returns animeInfoDto

        turbineScope {
            homeDataRepositoryImpl.getUserInfo().test {
                homeDataRepositoryImpl.getUserInfo()
                val lastWatched = animeInfoDto.toAnimeDetailInfo().toLastWatched()
                TestCase.assertEquals(User(lastWatchedAnime = lastWatched), awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }

        coVerify { appNetworkClient.searchAnimeById(-1, any()) }
    }

    @Test
    fun getUserInfoEmptyLastWatchedTest() = runTest {
        initRepository()
        coEvery { userDataRepository.getUserInfo() } returns User()

        turbineScope {
            homeDataRepositoryImpl.getUserInfo().test {
                homeDataRepositoryImpl.getUserInfo()
                TestCase.assertEquals(User(), awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }

        coVerify(exactly = 0) { appNetworkClient.searchAnimeById(any(), any()) }
    }

    @Test
    fun getUserInfoBadRequestTest() = runTest {
        initRepository()
        coEvery { userDataRepository.getUserInfo() } returns User(lastWatchedAnime = null)
        coEvery { appNetworkClient.searchAnimeById(-1, any()) } returns getDefaultHttpResponse(400)

        turbineScope {
            homeDataRepositoryImpl.getUserInfo().test {
                homeDataRepositoryImpl.getUserInfo()
                TestCase.assertEquals(User(lastWatchedAnime = null), awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }

        coVerify { appNetworkClient.searchAnimeById(-1, any()) }
    }
}