package com.filinoadrian.githubuserfinder.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.filinoadrian.githubuserfinder.R
import com.filinoadrian.githubuserfinder.databinding.FragmentSearchBinding
import com.filinoadrian.githubuserfinder.ui.search.SearchViewModel.Companion.FIRST_PAGE
import com.filinoadrian.githubuserfinder.ui.search.adapter.ItemDecoration
import com.filinoadrian.githubuserfinder.ui.search.adapter.UsersAdapter
import com.filinoadrian.githubuserfinder.util.*
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val searchViewModel by viewModels<SearchViewModel> { viewModelFactory }

    private lateinit var adapter: UsersAdapter

    private lateinit var scrollListener: RecyclerViewLoadMoreScroll

    private lateinit var viewDataBinding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentSearchBinding.inflate(inflater, container, false)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initSearchView()
        initAdapter()
        initScrollListener()
        initObserverLiveData()
    }

    private fun initSearchView() {
        viewDataBinding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!requireActivity().isConnected()) {
                    showErrorDialog(R.string.offline_title, R.string.offline_subtitle)
                    return false
                }

                if (query.isNotEmpty()) {
                    requireActivity().hideSoftKeyboard()
                    adapter.clearData()
                    searchViewModel.searchUsers(query)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun initAdapter() {
        adapter = UsersAdapter()
        viewDataBinding.userList.adapter = adapter
        viewDataBinding.userList.addItemDecoration(ItemDecoration(resources.getDimensionPixelSize(R.dimen.padding_small)))
    }

    private  fun initScrollListener() {
        scrollListener = RecyclerViewLoadMoreScroll(viewDataBinding.userList.layoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData()
            }
        })
        viewDataBinding.userList.addOnScrollListener(scrollListener)
    }

    private fun initObserverLiveData() {
        searchViewModel.items.observe(this.viewLifecycleOwner, {
            adapter.removeLoadingView()
            adapter.addData(it)
            scrollListener.setLoaded()
        })

        searchViewModel.isError.observe(this.viewLifecycleOwner, {
            showErrorDialog(R.string.error_fetching_data)
        })

        searchViewModel.isEmpty.observe(this.viewLifecycleOwner, { isEmpty ->
            viewDataBinding.noUserFound.visibility = toVisibility(isEmpty)
        })

        searchViewModel.isLoading.observe(this.viewLifecycleOwner, { isLoading ->
            viewDataBinding.progressBar.visibility = toVisibility(isLoading && searchViewModel.currentPageValue == FIRST_PAGE)
        })
    }

    private fun loadMoreData() {
        if (!requireActivity().isConnected()) {
            showErrorDialog(R.string.offline_title, R.string.offline_subtitle)
            return
        }

        if (adapter.itemCount < searchViewModel.totalCount) {
            adapter.addLoadingView()
            searchViewModel.loadMore()
        }
    }

    private fun showErrorDialog(title: Int, subtitle: Int? = null) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setPositiveButton(R.string.ok) { _, _ -> }

        subtitle?.let {
            dialog.setMessage(subtitle)
        }

        dialog.show()
    }
}