package com.tistory.blackjin.birdviewchallenge.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingViewHolder<B : ViewDataBinding>(
    @LayoutRes layoutRes: Int,
    parent: ViewGroup?,
    private val variableId: Int?
) : BaseViewHolder(
    LayoutInflater.from(parent?.context)
        .inflate(layoutRes, parent, false)
) {

    val binding: B = DataBindingUtil.bind(itemView)!!

    override fun onBindViewHolder(item: Any?) {
        try {
            binding.run {
                variableId?.let {
                    setVariable(it, item)
                }
                executePendingBindings()
            }
            itemView.visibility = View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
            itemView.visibility = View.GONE
        }
    }
}