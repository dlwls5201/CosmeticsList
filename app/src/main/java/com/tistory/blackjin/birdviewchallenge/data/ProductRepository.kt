package com.tistory.blackjin.birdviewchallenge.data

import com.tistory.blackjin.birdviewchallenge.constant.SkinType
import com.tistory.blackjin.birdviewchallenge.data.model.mapToPresentation
import com.tistory.blackjin.birdviewchallenge.data.source.remote.ProductService
import com.tistory.blackjin.birdviewchallenge.util.SchedulersProvider
import io.reactivex.Single

class ProductRepository(
    private val productService: ProductService,
    private val schedulersProvider: SchedulersProvider
) {
    fun get(page: Int = 1) = productService.getProducts(SkinType.oily, page)
        .flatMap {
            if (it.statusCode == 200) {
                val items = it.body.map { item ->
                    item.mapToPresentation(200, 200)
                }
                Single.just(items)
            } else {
                error("error : ${it.statusCode}")
            }
        }
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())
}