package com.filinoadrian.githubuserfinder.data.source

import com.filinoadrian.githubuserfinder.data.Result
import com.filinoadrian.githubuserfinder.data.source.remote.api.UserSearchResponse
import com.filinoadrian.githubuserfinder.model.UserEntity

interface UsersRepository {

    suspend fun searchUsers(query: String, page: Int): Result<UserSearchResponse>
}