package com.tistory.blackjin.birdviewchallenge.data.model

import com.google.gson.annotations.SerializedName
import com.tistory.blackjin.birdviewchallenge.presenter.model.ProductItem

data class Product(
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("oily_score")
    val oilyScore: Int,
    @SerializedName("dry_score")
    val dryScore: Int,
    @SerializedName("sensitive_score")
    val sensitiveScore: Int,
    @SerializedName("thumbnail_image")
    val thumbnailImage: String,
    @SerializedName("title")
    val title: String
)

fun Product.mapToPresentation(imgWidth: Int, imgHeight: Int) = ProductItem(
    id = id,
    thumbnailImage = thumbnailImage,
    imgWidth = imgWidth,
    imgHeight = imgHeight
)