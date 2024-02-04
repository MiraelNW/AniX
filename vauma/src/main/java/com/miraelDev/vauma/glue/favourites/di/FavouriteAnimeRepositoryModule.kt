package com.miraelDev.vauma.glue.favourites.di

import com.miraelDev.vauma.glue.favourites.repository.FavouriteAnimeRepositoryImpl
import com.miraeldev.favourites.data.FavouriteAnimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface FavouriteAnimeRepositoryModule {

    @Binds
    fun bindFavouriteAnimeRepository(impl: FavouriteAnimeRepositoryImpl): FavouriteAnimeRepository

}