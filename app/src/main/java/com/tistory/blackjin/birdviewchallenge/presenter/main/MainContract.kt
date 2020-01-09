package com.tistory.blackjin.birdviewchallenge.presenter.main

import com.tistory.blackjin.birdviewchallenge.presenter.BaseView
import com.tistory.blackjin.birdviewchallenge.presenter.model.ProductItem

interface MainContract {

    interface View : BaseView<Presenter> {

        fun showProduct(items: List<ProductItem>)

        fun showAddProduct(items: List<ProductItem>)

        fun showBottomProductLoading()

        fun hideBottomProductLoading()

        fun showLoading()

        fun hideLoading()

        fun showToast(message: String?)

        fun showEmptyText()

        fun hideEmptyText()
    }

    interface Presenter {

        fun loadProduct()

        fun searchProduct(search: String)

        fun addProduct()

        fun initData()
    }
}