package com.tistory.blackjin.birdviewchallenge.data

import com.google.gson.Gson
import com.tistory.blackjin.birdviewchallenge.constant.SkinType
import com.tistory.blackjin.birdviewchallenge.data.error.MyHttpException
import com.tistory.blackjin.birdviewchallenge.data.model.Product
import com.tistory.blackjin.birdviewchallenge.data.model.Response
import com.tistory.blackjin.birdviewchallenge.data.model.mapToPresentation
import com.tistory.blackjin.birdviewchallenge.data.source.remote.ProductService
import com.tistory.blackjin.birdviewchallenge.presenter.model.ProductItem
import com.tistory.blackjin.birdviewchallenge.util.SchedulersProvider
import io.reactivex.Single

class ProductRepository(
    private val productService: ProductService,
    private val schedulersProvider: SchedulersProvider
) {

    fun get(page: Int = 1) =
        productService.getProducts(SkinType.oily, page)
            .flatMap(productMapping)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun search(page: Int = 1, search: String) =
        productService.searchProducts(SkinType.oily, page, search)
            .flatMap(productMapping)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    private val gson = Gson()

    private val productMapping: (Response<Any>) -> Single<List<ProductItem>> = {
        if (it.statusCode == 200) {
            val products = gson.fromJson(
                gson.toJson(it.body),
                Array<Product>::class.java
            )
                .toList()
                .map { product ->
                    product.mapToPresentation()
                }

            Single.just(products)
        } else {
            Single.error(
                MyHttpException(it.statusCode, it.body.toString())
            )
        }
    }

}