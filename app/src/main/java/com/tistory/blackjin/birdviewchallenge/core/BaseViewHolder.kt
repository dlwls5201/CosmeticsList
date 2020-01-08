
package com.tistory.blackjin.birdviewchallenge.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBindViewHolder(item: Any?)
}