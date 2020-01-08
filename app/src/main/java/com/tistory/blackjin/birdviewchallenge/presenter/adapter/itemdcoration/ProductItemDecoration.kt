package com.tistory.blackjin.birdviewchallenge.presenter.adapter.itemdcoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tistory.blackjin.birdviewchallenge.presenter.adapter.ProductAdapter

class ProductItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        val lp = parent.layoutManager as GridLayoutManager
        val spanIndex = lp.spanSizeLookup.getSpanIndex(position, ProductAdapter.spanCount)

        if (spanIndex == 0) {
            outRect.right = 20
            outRect.left = 40
        } else if (spanIndex == 1) {
            outRect.left = 20
            outRect.right = 40
        }
    }
}