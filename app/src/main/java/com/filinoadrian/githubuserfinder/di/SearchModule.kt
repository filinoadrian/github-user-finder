package com.filinoadrian.githubuserfinder.di

import androidx.lifecycle.ViewModel
import com.filinoadrian.githubuserfinder.ui.search.SearchFragment
import com.filinoadrian.githubuserfinder.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SearchModule {

    @ContributesAndroidInjector
    internal abstract fun contributeSearchFragment(): SearchFragment

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel
}