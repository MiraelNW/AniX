package com.miraelDev.anix.di

import android.app.Application
import com.miraelDev.anix.data.Repository.AnimeListRepositoryImpl
import com.miraelDev.anix.data.Repository.FilterRepositoryImpl
import com.miraelDev.anix.data.Repository.SearchAnimeRepositoryImpl
import com.miraelDev.anix.data.local.AppDataBase
import com.miraelDev.anix.data.local.Dao.FavouriteAnimeDao
import com.miraelDev.anix.data.local.Dao.SearchAnimeDao
import com.miraelDev.anix.domain.repository.AnimeListRepository
import com.miraelDev.anix.domain.repository.FilterAnimeRepository
import com.miraelDev.anix.domain.repository.SearchAnimeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindAnimeListRepository(impl: AnimeListRepositoryImpl): AnimeListRepository

    @Binds
    @ApplicationScope
    fun bindSearchAnimeRepository(impl: SearchAnimeRepositoryImpl): SearchAnimeRepository

    @Binds
    @ApplicationScope
    fun bindFilterRepository(impl: FilterRepositoryImpl): FilterAnimeRepository

    companion object {
        @Provides
        fun provideSearchAnimeDao(application: Application): SearchAnimeDao {
            return AppDataBase.getInstance(application).searchAnimeDao()
        }

        @Provides
        fun provideFavouriteAnimeDao(application: Application): FavouriteAnimeDao {
            return AppDataBase.getInstance(application).favouriteAnimeDao()
        }
    }

}