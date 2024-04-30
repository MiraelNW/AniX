package com.miraelDev.vauma.glue.search.repositories

import com.miraeldev.anime.CategoryModel
import com.miraeldev.api.FilterAnimeDataRepository
import com.miraeldev.search.data.repository.FilterAnimeRepository
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class FilterRepositoryImpl(
    private val filterAnimeDataRepository: FilterAnimeDataRepository
) : FilterAnimeRepository {
    override fun getGenreList(): Flow<List<CategoryModel>> {
        return filterAnimeDataRepository.getGenreList()
    }

    override fun getYearCategory(): Flow<String> {
        return filterAnimeDataRepository.getYearCategory()
    }

    override fun getSortByCategory(): Flow<String> {
        return filterAnimeDataRepository.getSortByCategory()
    }

    override fun selectCategory(categoryId: Int, category: String) {
        filterAnimeDataRepository.selectCategory(categoryId, category)
    }

    override suspend fun clearAllFilters() {
        filterAnimeDataRepository.clearAllFilters()
    }
}