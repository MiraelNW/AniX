package com.miraelDev.anix.di

import androidx.lifecycle.ViewModel
import com.miraelDev.anix.presentation.AnimeListScreen.AnimeListViewModel
import com.miraelDev.anix.presentation.FavouriteListScreen.FavouriteAnimeViewModel
import com.miraelDev.anix.presentation.SearchAimeScreen.FilterScreen.FilterViewModel
import com.miraelDev.anix.presentation.SearchAimeScreen.SearchAnimeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @Binds
    @ViewModelKey(SearchAnimeViewModel::class)
    fun bindSearchAnimeViewModel(viewModel: SearchAnimeViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(AnimeListViewModel::class)
    fun bindAnimeListViewModel(viewModel: AnimeListViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(FavouriteAnimeViewModel::class)
    fun bindFavouriteAnimeViewModel(viewModel: FavouriteAnimeViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(FilterViewModel::class)
    fun bindFilterViewModel(viewModel: FilterViewModel): ViewModel

}