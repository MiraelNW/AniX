package com.miraeldev.features_detailinfo

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.detailinfo.domain.useCases.DownloadAnimeEpisodeUseCase
import com.miraeldev.detailinfo.domain.useCases.GetAnimeDetailUseCase
import com.miraeldev.detailinfo.domain.useCases.LoadAnimeDetailUseCase
import com.miraeldev.detailinfo.domain.useCases.LoadVideoIdUseCase
import com.miraeldev.detailinfo.domain.useCases.SelectAnimeItemUseCase
import com.miraeldev.detailinfo.presentation.detailComponent.DetailStore
import com.miraeldev.detailinfo.presentation.detailComponent.DetailStoreFactory
import com.miraeldev.models.result.FailureCauses
import com.miraeldev.models.result.ResultAnimeDetail
import io.mockk.coVerify
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DetailStoreFactoryTest {

    @get:Rule
    val rule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val storeFactory = DefaultStoreFactory()

    private val getAnimeDetailUseCase: GetAnimeDetailUseCase = mockk(relaxed = true)
    private val selectAnimeItemUseCase: SelectAnimeItemUseCase = mockk(relaxed = true)
    private val loadVideoIdUseCase: LoadVideoIdUseCase = mockk(relaxed = true)
    private val loadAnimeDetailUseCase: LoadAnimeDetailUseCase = mockk(relaxed = true)
    private val downloadAnimeEpisodeUseCase: DownloadAnimeEpisodeUseCase = mockk(relaxed = true)

    private fun store(): DetailStore = DetailStoreFactory(
        storeFactory,
        getAnimeDetailUseCase,
        selectAnimeItemUseCase,
        loadVideoIdUseCase,
        loadAnimeDetailUseCase,
        downloadAnimeEpisodeUseCase
    ).create()

    @Test
    fun checkStateAfterSuccessDataLoading() = runTest {
        every { getAnimeDetailUseCase() } returns flowOf(ResultAnimeDetail.Success(listOf()))

        val store = store()

        assertEquals(
            DetailStore.State.AnimeDetailScreenState.SearchResult(persistentListOf()),
            store.state.animeDetailScreenState
        )
    }

    @Test
    fun checkStateAfterFailureDataLoading() = runTest {
        every { getAnimeDetailUseCase() } returns flowOf(ResultAnimeDetail.Failure(FailureCauses.BadServer))

        val store = store()

        assertEquals(
            DetailStore.State.AnimeDetailScreenState.SearchFailure(FailureCauses.BadServer),
            store.state.animeDetailScreenState
        )
    }

    @Test
    fun checkOnBackClickEvent() = runTest {
        val store = store()

        turbineScope {
            store.labels.test {
                store.accept(DetailStore.Intent.OnBackClicked)

                assertEquals(
                    DetailStore.Label.OnBackClicked,
                    awaitItem()
                )
            }
        }
    }

    @Test
    fun checkOnSeriesClickEvent() = runTest {
        val store = store()

        turbineScope {
            store.labels.test {
                store.accept(DetailStore.Intent.OnSeriesClick)

                assertEquals(
                    DetailStore.Label.OnSeriesClick,
                    awaitItem()
                )
            }
        }
    }

    @Test
    fun checkOnAnimeItemClickEvent() = runTest {
        val store = store()
        val id = 123

        turbineScope {
            store.labels.test {
                store.accept(DetailStore.Intent.OnAnimeItemClick(id))

                assertEquals(
                    DetailStore.Label.OnAnimeItemClick(id),
                    awaitItem()
                )
            }
        }
    }

    @Test
    fun checkLoadAnimeDetailClickEvent() = runTest {
        val store = store()
        val id = 123

        store.accept(DetailStore.Intent.LoadAnimeDetail(id))

        coVerify { loadAnimeDetailUseCase(id) }
    }

    @Test
    fun checkDownloadEpisodeClickEvent() = runTest {
        val store = store()
        val url = "url"
        val videoName = "videoName"

        store.accept(DetailStore.Intent.DownloadEpisode(url, videoName))

        coVerify { downloadAnimeEpisodeUseCase(url, videoName) }
    }

    @Test
    fun checkLoadAnimeVideoClickEvent() = runTest {
        val store = store()
        val animeDetailInfo = AnimeDetailInfo()
        val id = 123

        store.accept(DetailStore.Intent.LoadAnimeVideo(AnimeDetailInfo(), id))

        coVerify { loadVideoIdUseCase(animeDetailInfo, id) }
    }

    @Test
    fun checkSelectAnimeItemClickEvent() = runTest {
        val store = store()
        val animeDetailInfo = AnimeDetailInfo()

        store.accept(DetailStore.Intent.SelectAnimeItem(true, animeDetailInfo))

        coVerify { selectAnimeItemUseCase(true, animeDetailInfo) }

        store.accept(DetailStore.Intent.SelectAnimeItem(false, animeDetailInfo))

        coVerify { selectAnimeItemUseCase(false, animeDetailInfo) }
    }
}