package com.filinoadrian.githubuserfinder

import com.filinoadrian.githubuserfinder.data.source.UsersRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestApplicationModule {

    @Singleton
    @Provides
    fun provideRepository(): UsersRepository = FakeRepository()
}