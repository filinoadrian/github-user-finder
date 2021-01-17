package com.filinoadrian.githubuserfinder.data.source.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int
    ): UserSearchResponse
}