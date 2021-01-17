package com.filinoadrian.githubuserfinder.di

import com.filinoadrian.githubuserfinder.data.source.UsersRepositoryImpl
import com.filinoadrian.githubuserfinder.data.source.UsersDataSource
import com.filinoadrian.githubuserfinder.data.source.UsersRepository
import com.filinoadrian.githubuserfinder.data.source.remote.UsersRemoteDataSource
import com.filinoadrian.githubuserfinder.data.source.remote.api.GithubService
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module(includes = [ApplicationModuleBinds::class])
class ApplicationModule {

    @Singleton
    @Provides
    fun provideUsersRemoteDataSource(
        service: GithubService,
        ioDispatcher: CoroutineDispatcher
    ): UsersDataSource {
        return UsersRemoteDataSource(
            service, ioDispatcher
        )
    }
}

@Module
abstract class ApplicationModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: UsersRepositoryImpl): UsersRepository
}