package com.miraelDev.vauma.glue.detailInfo.di

import com.miraelDev.vauma.glue.detailInfo.repositories.AnimeDetailInfoRepositoryImpl
import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface AnimeDetailInfoRepositoryModule {

    @Binds
    fun bindAnimeDetailInfoRepository(impl: AnimeDetailInfoRepositoryImpl): AnimeDetailRepository


}