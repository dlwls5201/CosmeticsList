package com.tistory.blackjin.birdviewchallenge.data.model

import com.google.gson.annotations.SerializedName
import com.tistory.blackjin.birdviewchallenge.presenter.model.ProductItem

data class Product(
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("price")
    val price: String = "",
    @SerializedName("oily_score")
    val oilyScore: Int = 0,
    @SerializedName("dry_score")
    val dryScore: Int = 0,
    @SerializedName("sensitive_score")
    val sensitiveScore: Int = 0,
    @SerializedName("thumbnail_image")
    val thumbnailImage: String = "",
    @SerializedName("title")
    val title: String = ""
)

fun Product.mapToPresentation() = ProductItem(
    id = id,
    thumbnailImage = thumbnailImage,
    title = title
)