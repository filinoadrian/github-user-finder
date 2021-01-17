package com.filinoadrian.githubuserfinder.domain

import com.filinoadrian.githubuserfinder.data.Result
import com.filinoadrian.githubuserfinder.data.source.UsersRepositoryImpl
import com.filinoadrian.githubuserfinder.data.source.remote.api.UserSearchResponse
import com.filinoadrian.githubuserfinder.model.UserEntity
import com.filinoadrian.githubuserfinder.util.TestDataGenerator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class SearchUsersUseCaseTest {

    private val mockUsersRepository = mock(UsersRepositoryImpl::class.java)

    private val searchUsersUseCase = SearchUsersUseCase(mockUsersRepository)

    @Test
    fun verifyResultIsEmptyWhenRepoMockReturnSuccessWithEmptyList() = runBlockingTest {
        // GIVEN
        val usersList: List<UserEntity> = listOf()
        val userSearchResponse = UserSearchResponse(totalCount = usersList.size, items = usersList)

        given(mockUsersRepository.searchUsers("filino", 1)).willReturn(Result.Success(userSearchResponse))

        // WHEN
        val result = searchUsersUseCase("filino", 1) as Result.Success

        // THEN
        assertTrue(result.data.items.isEmpty())
        assertEquals(result.data.totalCount, 0)
    }

    @Test
    fun verifyResultIsNotEmptyWhenRepoMockReturnSuccessWithNonEmptyList() = runBlockingTest {
        // GIVEN
        val usersList: List<UserEntity> = TestDataGenerator.getUsersEntity()
        val userSearchResponse = UserSearchResponse(totalCount = usersList.size, items = usersList)

        given(mockUsersRepository.searchUsers("filino", 1)).willReturn(Result.Success(userSearchResponse))

        // WHEN
        val result = searchUsersUseCase("filino", 1) as Result.Success

        // THEN
        assertEquals(result.data.items.size, 3)
        assertEquals(result.data.totalCount, 3)
    }

    @Test
    fun verifyResultErrorWhenRepoMockReturnError() = runBlockingTest {
        // GIVEN
        given(mockUsersRepository.searchUsers("filino", 1)).willReturn(Result.Error(Exception()))

        // WHEN
        val result = searchUsersUseCase("filino", 1)

        // THEN
        assertTrue(result is Result.Error)
    }
}