package com.filinoadrian.githubuserfinder.data.source.remote

import com.filinoadrian.githubuserfinder.data.Result
import com.filinoadrian.githubuserfinder.data.source.UsersDataSource
import com.filinoadrian.githubuserfinder.data.source.remote.api.GithubService
import com.filinoadrian.githubuserfinder.data.source.remote.api.UserSearchResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(
    private val service: GithubService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UsersDataSource {

    override suspend fun searchUsers(query: String, page: Int): Result<UserSearchResponse> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(service.searchUsers(query, page))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}