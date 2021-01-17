package com.filinoadrian.githubuserfinder.domain

import com.filinoadrian.githubuserfinder.data.Result
import com.filinoadrian.githubuserfinder.data.source.UsersRepository
import com.filinoadrian.githubuserfinder.data.source.remote.api.UserSearchResponse
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    suspend operator fun invoke(query: String, page: Int): Result<UserSearchResponse> {
        return repository.searchUsers(query, page)
    }
}