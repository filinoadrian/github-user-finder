package com.filinoadrian.githubuserfinder

import com.filinoadrian.githubuserfinder.data.Result
import com.filinoadrian.githubuserfinder.data.source.UsersRepository
import com.filinoadrian.githubuserfinder.data.source.remote.api.UserSearchResponse

class FakeRepository : UsersRepository {

    private lateinit var userSearchResponse: UserSearchResponse

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun searchUsers(query: String, page: Int): Result<UserSearchResponse> {
        if (shouldReturnError) {
            return Result.Error(Exception("Test exception"))
        }
        return Result.Success(userSearchResponse)
    }
}