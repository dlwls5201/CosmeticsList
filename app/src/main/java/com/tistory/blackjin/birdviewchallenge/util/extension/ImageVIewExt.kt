package com.tistory.blackjin.birdviewchallenge.util.extension

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    url?.let { imageUrl ->

        val holder = ColorDrawable(
            ContextCompat.getColor(context, android.R.color.darker_gray)
        )

        Glide.with(context)
            .load(imageUrl)
            .placeholder(holder)
            .into(this)
    }
}