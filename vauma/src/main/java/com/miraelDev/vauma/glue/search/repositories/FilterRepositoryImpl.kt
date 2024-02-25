package com.miraelDev.vauma.glue.search.repositories

import com.miraeldev.FilterAnimeDataRepository
import com.miraeldev.anime.CategoryModel
import com.miraeldev.search.data.repository.FilterAnimeRepository
import kotlinx.coroutines.flow.StateFlow
import me.tatarka.inject.annotations.Inject

@Inject
class FilterRepositoryImpl(
    private val filterAnimeDataRepository: FilterAnimeDataRepository
) : FilterAnimeRepository {
    override fun getGenreList(): StateFlow<List<CategoryModel>> {
        return filterAnimeDataRepository.getGenreList()
    }

    override fun getYearCategory(): StateFlow<String> {
        return filterAnimeDataRepository.getYearCategory()
    }

    override fun getSortByCategory(): StateFlow<String> {
        return filterAnimeDataRepository.getSortByCategory()
    }

    override fun selectCategory(categoryId: Int, category: String) {
        filterAnimeDataRepository.selectCategory(categoryId, category)
    }

    override suspend fun clearAllFilters() {
        filterAnimeDataRepository.clearAllFilters()
    }
}