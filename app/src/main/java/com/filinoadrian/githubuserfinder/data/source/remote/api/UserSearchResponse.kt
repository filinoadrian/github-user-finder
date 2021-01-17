package com.filinoadrian.githubuserfinder.data.source.remote.api

import com.filinoadrian.githubuserfinder.model.UserEntity
import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    @SerializedName("total_count")
    val totalCount: Int = 0,
    @SerializedName("items")
    val items: List<UserEntity>
)