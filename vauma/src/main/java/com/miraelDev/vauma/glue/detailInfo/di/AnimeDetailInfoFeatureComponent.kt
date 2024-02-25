package com.miraelDev.vauma.glue.detailInfo.di

import com.miraelDev.vauma.glue.detailInfo.repositories.AnimeDetailInfoRepositoryImpl
import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import me.tatarka.inject.annotations.Provides

interface AnimeDetailInfoFeatureComponent {

    @Provides
    fun AnimeDetailInfoRepositoryImpl.bind(): AnimeDetailRepository = this
}