package com.filinoadrian.githubuserfinder.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class CoroutineModule {

    @Singleton
    @Provides
    fun provideDispatcher() = Dispatchers.IO
}