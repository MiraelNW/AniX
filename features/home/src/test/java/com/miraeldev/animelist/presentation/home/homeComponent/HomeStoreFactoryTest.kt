package com.miraeldev.animelist.presentation.home.homeComponent

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.miraeldev.animelist.domain.useCases.AddAnimeToListUseCase
import com.miraeldev.animelist.domain.useCases.GetUserInfoUseCase
import com.miraeldev.animelist.domain.useCases.LoadVideoIdUseCase
import com.miraeldev.animelist.domain.useCases.homeScreenUseCases.LoadDataUseCase
import com.miraeldev.models.anime.LastWatchedAnime
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HomeStoreFactoryTest {

    @get:Rule
    val rule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val storeFactory = DefaultStoreFactory()
    private val loadDataUseCase: LoadDataUseCase = mockk(relaxed = true)
    private val addAnimeToListUseCase: AddAnimeToListUseCase = mockk(relaxed = true)
    private val getUserInfoUseCase: GetUserInfoUseCase = mockk(relaxed = true)
    private val loadVideoIdUseCase: LoadVideoIdUseCase = mockk(relaxed = true)

    private fun store(): HomeStore = HomeStoreFactory(
        storeFactory,
        loadDataUseCase,
        addAnimeToListUseCase,
        getUserInfoUseCase,
        loadVideoIdUseCase
    ).create()

    @Test
    fun checkLoadAnimeVideoEvent() = runTest {
        val store = store()
        val anime = LastWatchedAnime(123)

        store.accept(HomeStore.Intent.LoadAnimeVideo(anime))

        coVerify { loadVideoIdUseCase(anime) }
    }

    @Test
    fun checkAddAnimeToListEvent() = runTest {
        val store = store()
        val anime = LastWatchedAnime(123)
        val selected = true

        store.accept(HomeStore.Intent.AddAnimeToList(selected, anime))

        coVerify { addAnimeToListUseCase(selected, anime) }
    }

    @Test
    fun checkOnAnimeItemClickClickEvent() = runTest {
        val store = store()
        val id = 123

        turbineScope {
            store.labels.test {
                store.accept(HomeStore.Intent.OnAnimeItemClick(id))

                TestCase.assertEquals(
                    HomeStore.Label.OnAnimeItemClick(id),
                    awaitItem()
                )
            }
        }
    }

    @Test
    fun checkOnSeeAllClickClickEvent() = runTest {
        val store = store()
        val id = 123

        turbineScope {
            store.labels.test {
                store.accept(HomeStore.Intent.OnSeeAllClick(id))

                TestCase.assertEquals(
                    HomeStore.Label.OnSeeAllClick(id),
                    awaitItem()
                )
            }
        }
    }

    @Test
    fun checkOnPlayClickClickEvent() = runTest {
        val store = store()
        val id = 123

        turbineScope {
            store.labels.test {
                store.accept(HomeStore.Intent.OnPlayClick(id))

                TestCase.assertEquals(
                    HomeStore.Label.OnPlayClick(id),
                    awaitItem()
                )
            }
        }
    }
}