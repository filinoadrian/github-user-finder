package com.filinoadrian.githubuserfinder.data.source

import com.filinoadrian.githubuserfinder.data.Result
import com.filinoadrian.githubuserfinder.data.source.remote.UsersRemoteDataSource
import com.filinoadrian.githubuserfinder.data.source.remote.api.UserSearchResponse
import com.filinoadrian.githubuserfinder.model.UserEntity
import com.filinoadrian.githubuserfinder.util.TestDataGenerator
import org.junit.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class UsersRepositoryImplTest {

    private lateinit var usersRepository: UsersRepositoryImpl

    private val mockUsersRemoteDataSource = mock(UsersRemoteDataSource::class.java)

    @ExperimentalCoroutinesApi
    @Before
    fun setupRepository() {
        usersRepository = UsersRepositoryImpl(mockUsersRemoteDataSource, Dispatchers.Unconfined)
    }

    @Test
    fun verifyResultIsNotEmptyWhenDataSourceMockReturnSuccessWithNonEmptyList() = runBlockingTest {
        // GIVEN
        val usersList: List<UserEntity> = TestDataGenerator.getUsersEntity()
        val userSearchResponse = UserSearchResponse(totalCount = usersList.size, items = usersList)

        given(mockUsersRemoteDataSource.searchUsers("filino", 1)).willReturn(Result.Success(userSearchResponse))

        // WHEN
        val result = usersRepository.searchUsers("filino", 1) as Result.Success

        // THEN
        assertEquals(result.data.items, usersList)
        assertEquals(result.data.totalCount, 3)
    }

    @Test
    fun verifyResultErrorWhenDataSourceMockReturnError() = runBlockingTest {
        // GIVEN
        given(mockUsersRemoteDataSource.searchUsers("filino", 1)).willReturn(Result.Error(Exception()))

        // WHEN
        val result = usersRepository.searchUsers("filino", 1)

        // THEN
        assertTrue(result is Result.Error)
    }
}