package com.tistory.blackjin.birdviewchallenge.presenter.main

import com.tistory.blackjin.birdviewchallenge.constant.SkinType
import com.tistory.blackjin.birdviewchallenge.data.ProductRepository
import com.tistory.blackjin.birdviewchallenge.data.error.MyHttpException
import io.reactivex.disposables.CompositeDisposable

class MainPresenter(
    private val view: MainContract.View,
    private val productRepo: ProductRepository,
    private val compositeDisposable: CompositeDisposable
) : MainContract.Presenter {

    private var page = 1
    private var search = ""
    private var skinType = SkinType.oily

    private var isBottomLoading = false

    override fun loadProduct() {
        productRepo.get(skinType, page)
            .doOnSubscribe {
                if(isBottomLoading) {
                    view.showBottomProductLoading()
                } else {
                    view.showLoading()
                }
            }
            .doOnSuccess {
                if(isBottomLoading) {
                    view.hideBottomProductLoading()
                } else {
                    view.hideLoading()
                }
            }
            .doOnError {
                if(isBottomLoading) {
                    view.hideBottomProductLoading()
                } else {
                    view.hideLoading()
                }
            }
            .subscribe({
                if(isBottomLoading) {
                    view.showAddProduct(it)
                    isBottomLoading = false
                } else {
                    view.showProduct(it)
                }
            }) {
                view.showToast(it.message)
            }.also {
                compositeDisposable.add(it)
            }
    }

    override fun searchProduct(search: String) {
        this.search = search

        productRepo.search(skinType, page, search)
            .doOnSubscribe {
                if(isBottomLoading) {
                    view.showBottomProductLoading()
                } else {
                    view.showLoading()
                    view.showProduct(emptyList())
                }
            }
            .doOnSuccess {
                if(isBottomLoading) {
                    view.hideBottomProductLoading()
                } else {
                    view.hideEmptyText()
                    view.hideLoading()
                }
            }
            .doOnError {
                if(isBottomLoading) {
                    view.hideBottomProductLoading()
                } else {
                    view.hideEmptyText()
                    view.hideLoading()
                }
            }
            .subscribe({
                if(isBottomLoading) {
                    view.showAddProduct(it)
                    isBottomLoading = false
                } else {
                    view.showProduct(it)
                }
            }) {
                if(it is MyHttpException &&
                    it.message.startsWith("ProductNotFound")) {
                    view.showEmptyText()
                } else {
                    view.showToast(it.message)
                }
            }.also {
                compositeDisposable.add(it)
            }
    }

    override fun addProduct() {
        if(!isBottomLoading) {
            page++
            isBottomLoading = true

            if(search.isNotEmpty()) {
                searchProduct(search)
            } else {
                loadProduct()
            }
        }
    }

    override fun initData() {
        page = 1
        search = ""

        //데이터 로딩 중에 초기화를 하면 기존 작업은 중단합니다.
        compositeDisposable.clear()

        if(isBottomLoading) {
            view.hideBottomProductLoading()
            isBottomLoading = false
        }
    }

    override fun changeSkinType(skinType: String) {
        this.skinType = skinType
    }
}