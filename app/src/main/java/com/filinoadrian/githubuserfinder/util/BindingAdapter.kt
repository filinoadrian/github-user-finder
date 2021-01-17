package com.filinoadrian.githubuserfinder.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.filinoadrian.githubuserfinder.R

@BindingAdapter("app:avatar")
fun setAvatar(view: ImageView, avatarUrl: String?) {
    if (!avatarUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(avatarUrl)
            .placeholder(R.drawable.ic_account)
            .circleCrop()
            .into(view)
    }
}