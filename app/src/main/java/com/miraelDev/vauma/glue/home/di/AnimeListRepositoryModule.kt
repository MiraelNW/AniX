package com.miraelDev.vauma.glue.home.di


import com.miraelDev.vauma.glue.home.repository.AnimeListRepositoryImpl
import com.miraeldev.animelist.data.AnimeListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface AnimeListRepositoryModule {

    @Binds
    fun bindAnimeListRepository(impl: AnimeListRepositoryImpl): AnimeListRepository

}