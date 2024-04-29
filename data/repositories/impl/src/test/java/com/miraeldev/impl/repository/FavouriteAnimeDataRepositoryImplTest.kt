package com.miraeldev.impl.repository

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.api.FavouriteAnimeDao
import com.miraeldev.result.FailureCauses
import com.miraeldev.result.ResultAnimeInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class FavouriteAnimeDataRepositoryImplTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var favouriteAnimeDao: FavouriteAnimeDao

    private lateinit var favouriteAnimeDataRepositoryImpl: FavouriteAnimeDataRepositoryImpl

    @RelaxedMockK
    lateinit var animeInfo: AnimeInfo

    private fun initRepository() {
        favouriteAnimeDataRepositoryImpl = FavouriteAnimeDataRepositoryImpl(favouriteAnimeDao)
    }

    @Test
    fun selectAnimeItemSuccessResponseTest() = runTest {
        coEvery { favouriteAnimeDao.insertFavouriteAnimeItem(any()) } returns Unit
        coEvery { favouriteAnimeDao.getFavouriteAnimeList(any()) } returns emptyFlow()
        initRepository()

        favouriteAnimeDataRepositoryImpl.selectAnimeItem(true, animeInfo)

        coVerify() { favouriteAnimeDao.insertFavouriteAnimeItem(any()) }
        coVerify(exactly = 0) { favouriteAnimeDao.deleteFavouriteAnimeItem(any()) }
    }

    @Test
    fun unSelectAnimeItemSuccessResponseTest() = runTest {
        coEvery { favouriteAnimeDao.deleteFavouriteAnimeItem(any()) } returns Unit
        coEvery { favouriteAnimeDao.getFavouriteAnimeList(any()) } returns emptyFlow()
        initRepository()

        favouriteAnimeDataRepositoryImpl.selectAnimeItem(false, animeInfo)

        coVerify(exactly = 0) { favouriteAnimeDao.insertFavouriteAnimeItem(any()) }
        coVerify() { favouriteAnimeDao.deleteFavouriteAnimeItem(any()) }
    }

    @Test
    fun loadAnimeListTest() = runTest {
        every { favouriteAnimeDao.getFavouriteAnimeList() } returns flowOf(listOf(animeInfo))
        initRepository()

        turbineScope {
            favouriteAnimeDataRepositoryImpl.getFavouriteAnimeList().test {
                favouriteAnimeDataRepositoryImpl.loadAnimeList()
                assertEquals(ResultAnimeInfo.Success(listOf(animeInfo)), awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun loadEmptyAnimeListTest() = runTest {
        every { favouriteAnimeDao.getFavouriteAnimeList() } returns flowOf(listOf())
        initRepository()

        turbineScope {
            favouriteAnimeDataRepositoryImpl.getFavouriteAnimeList().test {
                favouriteAnimeDataRepositoryImpl.loadAnimeList()
                assertEquals(ResultAnimeInfo.Failure(FailureCauses.NotFound), awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun findAnimeInDbTest() = runTest {
        coEvery { favouriteAnimeDao.getFavouriteAnimeList(any()) } returns emptyFlow()
        coEvery { favouriteAnimeDao.searchAnimeItem(any()) } returns listOf(animeInfo)
        initRepository()

        turbineScope {
            favouriteAnimeDataRepositoryImpl.getFavouriteAnimeList().test {
                favouriteAnimeDataRepositoryImpl.searchAnimeItemInDatabase("name")
                assertEquals(ResultAnimeInfo.Success(listOf(animeInfo)), awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun notFindAnimeInDbTest() = runTest {
        coEvery { favouriteAnimeDao.getFavouriteAnimeList(any()) } returns emptyFlow()
        coEvery { favouriteAnimeDao.searchAnimeItem(any()) } returns emptyList()
        initRepository()

        turbineScope {
            favouriteAnimeDataRepositoryImpl.getFavouriteAnimeList().test {
                favouriteAnimeDataRepositoryImpl.searchAnimeItemInDatabase("name")
                assertEquals(ResultAnimeInfo.Failure(FailureCauses.NotFound), awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}