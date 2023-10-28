package com.miraelDev.vauma.glue.search.di

import com.miraelDev.vauma.glue.search.repositories.FilterRepositoryImpl
import com.miraelDev.vauma.glue.search.repositories.SearchRepositoryImpl
import com.miraeldev.search.data.repository.FilterAnimeRepository
import com.miraeldev.search.data.repository.SearchAnimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface SearchAndFilterRepositoryModule {

    @Binds
    fun bindSearchRepository(impl:SearchRepositoryImpl):SearchAnimeRepository


    @Binds
    fun bindFilterAnimeRepository(impl:FilterRepositoryImpl):FilterAnimeRepository

}