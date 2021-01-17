package com.filinoadrian.githubuserfinder.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.filinoadrian.githubuserfinder.util.LiveDataTestUtil
import com.filinoadrian.githubuserfinder.MainCoroutineRule
import com.filinoadrian.githubuserfinder.data.Result
import com.filinoadrian.githubuserfinder.data.source.remote.api.UserSearchResponse
import com.filinoadrian.githubuserfinder.domain.SearchUsersUseCase
import com.filinoadrian.githubuserfinder.model.UserEntity
import com.filinoadrian.githubuserfinder.util.TestDataGenerator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private lateinit var searchViewModel: SearchViewModel

    private var mockSearchUsersUseCase = mock(SearchUsersUseCase::class.java)

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        searchViewModel = SearchViewModel(mockSearchUsersUseCase)
    }

    @Test
    fun verifyItemsLiveDataIsNotEmptyWhenUseCaseMockReturnSuccessWithNonEmptyList() {
        mainCoroutineRule.runBlockingTest {
            // GIVEN
            val usersList: List<UserEntity> = TestDataGenerator.getUsersEntity()
            val userSearchResponse = UserSearchResponse(totalCount = usersList.size, items = usersList)

            given(mockSearchUsersUseCase("filino", 1)).willReturn(Result.Success(userSearchResponse))

            // WHEN
            searchViewModel.searchUsers("filino")

            // THEN
            assertEquals(LiveDataTestUtil.getValue(searchViewModel.items).size, 3)
            assertEquals(searchViewModel.totalCount, 3)
            assertFalse(LiveDataTestUtil.getValue(searchViewModel.isEmpty))
        }
    }

    @Test
    fun verifyIsEmptyLiveDataIsTrueWhenUseCaseMockReturnSuccessWithEmptyList() {
        mainCoroutineRule.runBlockingTest {
            // GIVEN
            val usersList: List<UserEntity> = listOf()
            val userSearchResponse = UserSearchResponse(totalCount = usersList.size, items = usersList)

            given(mockSearchUsersUseCase("filino", 1)).willReturn(Result.Success(userSearchResponse))

            // WHEN
            searchViewModel.searchUsers("filino")

            // THEN
            assertEquals(LiveDataTestUtil.getValue(searchViewModel.items).size, 0)
            assertEquals(searchViewModel.totalCount, 0)
            assertTrue(LiveDataTestUtil.getValue(searchViewModel.isEmpty))
        }
    }

    @Test
    fun verifyIsErrorLiveDataIsTrueWhenUseCaseMockReturnError() {
        mainCoroutineRule.runBlockingTest {
            // GIVEN
            given(mockSearchUsersUseCase("filino", 1)).willReturn(Result.Error(Exception()))

            // WHEN
            searchViewModel.searchUsers("filino")

            // THEN
            assertTrue(LiveDataTestUtil.getValue(searchViewModel.isError))
        }
    }

    @Test
    fun verifyIsLoadingLiveDataWhenLoadDataFromRepository() {
        mainCoroutineRule.runBlockingTest {
            // GIVEN
            val usersList: List<UserEntity> = listOf()
            val userSearchResponse = UserSearchResponse(totalCount = usersList.size, items = usersList)

            given(mockSearchUsersUseCase("filino", 1)).willReturn(Result.Success(userSearchResponse))

            // WHEN
            mainCoroutineRule.pauseDispatcher()
            searchViewModel.searchUsers("filino")

            // THEN
            assertTrue(LiveDataTestUtil.getValue(searchViewModel.isLoading))
            mainCoroutineRule.resumeDispatcher()
            assertFalse(LiveDataTestUtil.getValue(searchViewModel.isLoading))
        }
    }
}