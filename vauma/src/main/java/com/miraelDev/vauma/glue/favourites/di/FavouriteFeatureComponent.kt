package com.miraelDev.vauma.glue.favourites.di

import com.miraelDev.vauma.glue.favourites.repository.FavouriteAnimeRepositoryImpl
import com.miraeldev.favourites.data.FavouriteAnimeRepository
import me.tatarka.inject.annotations.Provides

interface FavouriteFeatureComponent {

    @Provides
    fun FavouriteAnimeRepositoryImpl.bind(): FavouriteAnimeRepository = this
}