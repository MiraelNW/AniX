package com.miraeldev.favourites.presentation.favouriteComponent

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.miraeldev.favourites.domain.useCases.GetFavouriteAnimeListUseCase
import com.miraeldev.favourites.domain.useCases.LoadAnimeListUseCase
import com.miraeldev.favourites.domain.useCases.SaveSearchTextUseCase
import com.miraeldev.favourites.domain.useCases.SearchAnimeItemInDatabaseUseCase
import com.miraeldev.favourites.domain.useCases.SelectAnimeItemUseCase
import com.miraeldev.models.anime.AnimeInfo
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class FavouriteStoreFactoryTest {

    @get:Rule
    val rule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val storeFactory = DefaultStoreFactory()

    private val getFavouriteAnimeListUseCase: GetFavouriteAnimeListUseCase = mockk(relaxed = true)
    private val selectAnimeItemUseCase: SelectAnimeItemUseCase = mockk(relaxed = true)
    private val searchAnimeItemInDatabaseUseCase: SearchAnimeItemInDatabaseUseCase = mockk(relaxed = true)
    private val loadAnimeListUseCase: LoadAnimeListUseCase = mockk(relaxed = true)
    private val saveSearchTextUseCase: SaveSearchTextUseCase = mockk(relaxed = true)

    private fun store(): FavouriteStore = FavouriteStoreFactory(
        storeFactory,
        getFavouriteAnimeListUseCase,
        selectAnimeItemUseCase,
        searchAnimeItemInDatabaseUseCase,
        loadAnimeListUseCase,
        saveSearchTextUseCase
    ).create()

    @Test
    fun checkOnBackClickEvent() = runTest {
        val store = store()
        val id = 123

        turbineScope {
            store.labels.test {
                store.accept(FavouriteStore.Intent.OnAnimeItemClick(id))

                TestCase.assertEquals(
                    FavouriteStore.Label.OnAnimeItemClicked(id),
                    awaitItem()
                )
            }
        }
    }

    @Test
    fun checkNavigateToSearchScreenClickEvent() = runTest {
        val store = store()
        val search = "123"

        turbineScope {
            store.labels.test {
                store.accept(FavouriteStore.Intent.NavigateToSearchScreen(search))

                TestCase.assertEquals(
                    FavouriteStore.Label.NavigateToSearchScreen(search),
                    awaitItem()
                )
            }
        }
    }

    @Test
    fun checkUpdateSearchTextState() = runTest {
        val store = store()
        val search = "123"
        store.accept(FavouriteStore.Intent.UpdateSearchTextState(search))

        TestCase.assertEquals(
            search,
            store.state.search
        )
    }

    @Test
    fun checkSelectAnimeItemClickEvent() = runTest {
        val store = store()
        val animeInfo = AnimeInfo(-1)

        store.accept(FavouriteStore.Intent.SelectAnimeItem(animeInfo))

        coVerify { selectAnimeItemUseCase(false, animeInfo) }
    }

    @Test
    fun checkSearchAnimeItemInDatabase() = runTest {
        val store = store()
        val name = "name"

        store.accept(FavouriteStore.Intent.SearchAnimeItemInDatabase(name))

        coVerify { searchAnimeItemInDatabaseUseCase(name) }
    }

    @Test
    fun checkSearchAnimeByName() = runTest {
        val store = store()
        val name = "name"

        store.accept(FavouriteStore.Intent.SearchAnimeByName(name))

        coVerify { saveSearchTextUseCase(name) }
    }

    @Test
    fun checkLoadAnimeList() = runTest {
        val store = store()

        store.accept(FavouriteStore.Intent.LoadAnimeList)

        coVerify { loadAnimeListUseCase() }
    }
}