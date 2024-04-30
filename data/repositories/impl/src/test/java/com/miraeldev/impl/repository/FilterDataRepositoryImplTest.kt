package com.miraeldev.impl.repository

import app.cash.turbine.turbineScope
import com.miraeldev.anime.CategoryModel
import com.miraeldev.data.repository.FilterDataRepositoryImpl
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class FilterDataRepositoryImplTest {
    @get:Rule
    val rule = MockKRule(this)

    private lateinit var filterDataRepositoryImpl: FilterDataRepositoryImpl

    private fun initRepository() {
        filterDataRepositoryImpl = FilterDataRepositoryImpl()
    }

    private val defaultGenresList = listOf(
        CategoryModel("shounen", false),
        CategoryModel("shojo", false),
        CategoryModel("comedy", false),
        CategoryModel("romance", false),
        CategoryModel("school", false),
        CategoryModel("martial_arts", false),
        CategoryModel("harem", false),
        CategoryModel("detective", false),
        CategoryModel("drama", false),
        CategoryModel("everyday_life", false),
        CategoryModel("adventure", false),
        CategoryModel("psychological", false),
        CategoryModel("supernatural", false),
        CategoryModel("sport", false),
        CategoryModel("horror", false),
        CategoryModel("fantastic", false),
        CategoryModel("fantasy", false),
        CategoryModel("action", false),
        CategoryModel("thriller", false),
        CategoryModel("superpower", false),
        CategoryModel("gourmet", false),
    )

    @Test
    fun clearAllFiltersTest() = runTest {
        initRepository()

        turbineScope {
            val genres = filterDataRepositoryImpl.getGenreList().testIn(backgroundScope)
            val year = filterDataRepositoryImpl.getYearCategory().testIn(backgroundScope)
            val sort = filterDataRepositoryImpl.getSortByCategory().testIn(backgroundScope)

            filterDataRepositoryImpl.clearAllFilters()

            assertEquals(defaultGenresList, genres.awaitItem())
            assertEquals("", year.awaitItem())
            assertEquals("", sort.awaitItem())
        }
    }
}