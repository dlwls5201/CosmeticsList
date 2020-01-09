package com.tistory.blackjin.birdviewchallenge.presenter.adapter

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.tistory.blackjin.birdviewchallenge.R
import com.tistory.blackjin.birdviewchallenge.presenter.model.ProductItem

class ProductAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<ProductItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ViewType.getViewType(viewType)) {
            ViewType.PRODUCT -> ProductViewHolder(parent)
            ViewType.LOADING -> LoadingViewHolder(parent)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductViewHolder) holder.bind(items[position])
    }

    fun replaceAll(items: List<ProductItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ProductViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
    ) {

        private val image = itemView.findViewById<ImageView>(R.id.ivItemProduct)
        private val title = itemView.findViewById<TextView>(R.id.tvItemProductTitle)

        private val holder = ColorDrawable(
            ContextCompat.getColor(itemView.context, android.R.color.darker_gray)
        )

        fun bind(item: ProductItem) {

            Glide.with(itemView.context)
                .load(item.thumbnailImage)
                .placeholder(holder)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(image)

            title.text = item.title
        }
    }

    class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
    )

    enum class ViewType {
        PRODUCT, LOADING;

        companion object {
            fun getViewType(value: Int) = values()[value]
        }
    }

    companion object {
        const val spanCount = 2
    }
}