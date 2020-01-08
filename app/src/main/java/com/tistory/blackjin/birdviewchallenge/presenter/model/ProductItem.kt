package com.tistory.blackjin.birdviewchallenge.presenter.model

import com.tistory.blackjin.birdviewchallenge.presenter.adapter.ProductAdapter

data class ProductItem(
    val id: Int = -1,
    val thumbnailImage: String = "",
    val title: String = "",
    val imgWidth: Int = 0,
    val imgHeight: Int = 0,
    val viewType: ProductAdapter.ViewType = ProductAdapter.ViewType.PRODUCT
)