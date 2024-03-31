package com.miraelDev.vauma.glue.search.di

import com.miraelDev.vauma.glue.search.repositories.FilterRepositoryImpl
import com.miraelDev.vauma.glue.search.repositories.SearchRepositoryImpl
import com.miraeldev.search.data.repository.FilterAnimeRepository
import com.miraeldev.search.data.repository.SearchAnimeRepository
import me.tatarka.inject.annotations.Provides


interface SearchFeatureComponent {

    @Provides
    fun SearchRepositoryImpl.bind(): SearchAnimeRepository = this

    @Provides
    fun FilterRepositoryImpl.bind(): FilterAnimeRepository = this
}