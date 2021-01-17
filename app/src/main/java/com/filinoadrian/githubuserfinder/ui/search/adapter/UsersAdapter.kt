package com.filinoadrian.githubuserfinder.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.filinoadrian.githubuserfinder.BR
import com.filinoadrian.githubuserfinder.databinding.ItemLoadingBinding
import com.filinoadrian.githubuserfinder.databinding.ItemSearchBinding
import com.filinoadrian.githubuserfinder.model.UserEntity

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var mUserList: ArrayList<UserEntity?> = ArrayList()

    fun addData(data: List<UserEntity>) {
        this.mUserList.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.mUserList.clear()
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        mUserList.add(null)
        notifyItemInserted(mUserList.size - 1)
    }

    fun removeLoadingView() {
        if (mUserList.isNotEmpty()) {
            mUserList.removeAt(mUserList.size - 1)
            notifyItemRemoved(mUserList.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = if (viewType == VIEW_TYPE_ITEM) {
            ItemSearchBinding.inflate(layoutInflater, parent, false)
        } else {
            ItemLoadingBinding.inflate(layoutInflater, parent, false)
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            holder.bind(mUserList[position]!!)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mUserList[position] == null) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return mUserList.size
    }

    class ViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(user: UserEntity) {
            viewDataBinding.setVariable(BR.user, user)
            viewDataBinding.executePendingBindings()
        }
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }
}