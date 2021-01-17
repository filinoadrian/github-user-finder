package com.filinoadrian.githubuserfinder.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filinoadrian.githubuserfinder.data.Result
import com.filinoadrian.githubuserfinder.domain.SearchUsersUseCase
import com.filinoadrian.githubuserfinder.model.UserEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase
) : ViewModel() {

    private val _items = MutableLiveData<List<UserEntity>>().apply { value = emptyList() }
    val items: LiveData<List<UserEntity>> = _items

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var currentQueryValue: String = ""
    var currentPageValue: Int = 1

    var totalCount: Int = 0

    fun searchUsers(query: String) {
        if (query != currentQueryValue) {
            currentQueryValue = query
            currentPageValue = 1
        }

        _isLoading.value = true
        _isEmpty.value = false

        viewModelScope.launch {
            val usersResult = searchUsersUseCase(currentQueryValue, currentPageValue)

            if (usersResult is Result.Success) {
                val usersList = usersResult.data.items
                totalCount = usersResult.data.totalCount

                if (usersList.isEmpty()) {
                    _isEmpty.value = true
                } else {
                    _items.value = usersList
                }
            } else if (usersResult is Result.Error){
                _isError.value = true
            }

            _isLoading.value = false
        }
    }

    fun loadMore() {
        currentPageValue++
        searchUsers(currentQueryValue)
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}