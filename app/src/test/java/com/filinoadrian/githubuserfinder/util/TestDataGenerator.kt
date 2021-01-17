package com.filinoadrian.githubuserfinder.util

import com.filinoadrian.githubuserfinder.model.UserEntity

object TestDataGenerator {

    fun getUsersEntity(): List<UserEntity> {
        return listOf(
            UserEntity(
                login = "filinova",
                id = 13554799,
                avatarUrl = "https://avatars0.githubusercontent.com/u/13554799?v=4"
            ),
            UserEntity(
                login = "filinamaria",
                id = 8943356,
                avatarUrl = "https://avatars1.githubusercontent.com/u/8943356?v=4"
            ),
            UserEntity(
                login = "filinoadrian",
                id = 18563489,
                avatarUrl = "https://avatars0.githubusercontent.com/u/18563489?v=4"
            )
        )
    }
}