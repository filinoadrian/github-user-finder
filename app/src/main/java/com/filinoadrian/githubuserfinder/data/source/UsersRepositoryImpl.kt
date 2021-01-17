package com.filinoadrian.githubuserfinder.data.source

import com.filinoadrian.githubuserfinder.data.Result
import com.filinoadrian.githubuserfinder.data.source.remote.api.UserSearchResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersRemoteDataSource: UsersDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UsersRepository {

    override suspend fun searchUsers(query: String, page: Int): Result<UserSearchResponse> {
        return withContext(ioDispatcher) {
            val usersResult = usersRemoteDataSource.searchUsers(query, page)

            when (usersResult) {
                is Result.Success -> return@withContext usersResult
                is Result.Error -> {
                    Timber.e(usersResult.exception)
                    return@withContext Result.Error(usersResult.exception)
                }
                else -> throw IllegalStateException()
            }
        }
    }
}