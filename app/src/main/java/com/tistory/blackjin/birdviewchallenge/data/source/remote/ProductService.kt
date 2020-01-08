package com.tistory.blackjin.birdviewchallenge.data.source.remote

import com.tistory.blackjin.birdviewchallenge.data.model.Product
import com.tistory.blackjin.birdviewchallenge.data.model.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("products")
    fun getProducts(
        @Query("skin_type") skinType: String ,
        @Query("page") page: Int
    ): Single<Response<List<Product>>>

    @GET("products")
    fun searchProducts(
        @Query("skin_type") skinType: String,
        @Query("page") page: Int,
        @Query("search") search: String
    ): Single<Response<List<Product>>>
}